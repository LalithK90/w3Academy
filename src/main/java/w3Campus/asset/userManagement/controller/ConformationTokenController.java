package w3Campus.asset.userManagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import w3Campus.asset.commonAsset.service.CommonService;
import w3Campus.asset.userManagement.entity.ConformationToken;
import w3Campus.asset.userManagement.entity.Role;
import w3Campus.asset.userManagement.entity.User;
import w3Campus.asset.userManagement.service.ConformationTokenService;
import w3Campus.asset.userManagement.service.RoleService;
import w3Campus.asset.userManagement.service.UserService;
import w3Campus.util.service.EmailService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ConformationTokenController {
    private final ConformationTokenService conformationTokenService;
    private final EmailService emailService;
    private final UserService userService;
    private final RoleService roleService;
    private final CommonService commonService;

    @Autowired
    public ConformationTokenController(ConformationTokenService conformationTokenService, EmailService emailService, UserService userService, RoleService roleService, CommonService commonService) {
        this.conformationTokenService = conformationTokenService;
        this.emailService = emailService;
        this.userService = userService;
        this.roleService = roleService;
        this.commonService = commonService;
    }

    @GetMapping(value = {"/register", "/forgottenPassword"})
    private String sendEmailForm(Model model, HttpServletRequest request) {
        String newOrOld;
        if (request.getRequestURI().equals("/forgottenPassword")) {
            newOrOld = "old";
        } else {
            newOrOld = "new";
        }
        model.addAttribute("newOrOld", newOrOld);
        return "user/register";
    }

    @PostMapping(value = {"/register"})
    private String sendTokenToEmail(@RequestParam("email") String email, @RequestParam("newOrOld") String newOrOld, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //if not email
        if (!commonService.isValidEmail(email)) {
            model.addAttribute("message", "Please enter valid email.");
            return "user/register";
        }
        //check if there is any user on system
        User user = userService.findByUserName(email);
        //before create the token need to check there is user on current email
        // if not create token else send forgotten password form to fill
        if (user != null && newOrOld.equals("new")) {
            model.addAttribute("message", "There is an user on system please forgotten password reset option.");
            return "user/register";
        }
        if (user != null && newOrOld.equals("old")) {
            //if newOrOld value is new but there is no user in system should be send error massage
            model.addAttribute("message", "There is no an user on system please sign up.");
            return "user/register";
        }
        //check if there is a valid token
        ConformationToken conformationToken = conformationTokenService.findByEmail(email);
        if (conformationToken != null && LocalDateTime.now().isBefore(conformationToken.getEndDate())) {
            model.addAttribute("message", "There is valid token fot this email " + email + " on the system. \n Please check your email.");
            return "user/register";
        }

        ConformationToken newConformationToken = new ConformationToken(email);
        String url = request.getRequestURL().toString();

        String message;
        if (newOrOld.equals("new")) {
            message = "Please click below link to active your account\n\n\t";
        } else {
            message = "Please Click below link to change password\n\n\t";
        }
        boolean emailSend = emailService.sendEmail(email, "Email Verification W3 (World Wide Wisdom) Campus - Not reply",
                message.concat(url + "/" + newOrOld + "/token/" + conformationTokenService.persist(newConformationToken).getToken()).concat("\n  this link is valid only one day. "));

        if (emailSend) {
            model.addAttribute("successMessage", "Please check your email \n Your entered email is \t ".concat(email));
            return "user/successMessage";
        } else {
            redirectAttributes.addFlashAttribute("newOrOld", "new");
            return "redirect:/register";
        }

    }

    @GetMapping(value = {"/register/{newOrOld}/token/{token}"})
    public String passwordEnterPage(@PathVariable("newOrOld") String newOrOld, @PathVariable("token") String token, Model model) {
        // need to check token has valid or not
        //check if there is a valid token
        ConformationToken conformationToken = conformationTokenService.findByToken(token);
        if (conformationToken != null && LocalDateTime.now().isBefore(conformationToken.getEndDate())) {
            model.addAttribute("token", conformationToken.getToken());
            model.addAttribute("newOrOld", newOrOld);
            return "user/password";
        }
        conformationTokenService.deleteByConformationToken(conformationToken);
        model.addAttribute("message", "There is no valid token.");
        return "user/register";
    }

    @PostMapping("/register/user")
    public String newUser(@RequestParam("token") String token, @RequestParam("password") String password,
                          @RequestParam("reEnterPassword") String reEnterPassword, @RequestParam("newOrOld") String newOrOld, Model model) {
//token
        ConformationToken conformationToken = conformationTokenService.findByToken(token);
        if (conformationToken == null) {
            model.addAttribute("message", "Your token is not valid \n re-register.");
            return "redirect:/register";
        }
        if (!password.equals(reEnterPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("newOrOld", newOrOld);
            model.addAttribute("message", "Entered password is missed match \n please re enter.");
            return "user/password";
        }
        //taken user according to token email
        User userDB = userService.findByUserName(conformationToken.getEmail());
        //check password matched or not or not in user db

        if (userDB != null && newOrOld.equals("old")) {
            userDB.setPassword(password);
            userService.persist(userDB);
        }
        if (userDB == null && newOrOld.equals("new")) {
            User user = new User();
            user.setEnabled(true);
            user.setUsername(conformationToken.getEmail());
            user.setPassword(password);
            List<Role> roles = new ArrayList<>();
            for (Role role : roleService.findAll()) {
                if (role.getRoleName().equals("STUDENT")) {
                    roles.add(role);
                    break;
                }
            }
            user.setRoles(roles);
            userService.persist(user);

        }
        if (userDB == null && newOrOld.equals("old") || userDB != null && newOrOld.equals("new")) {
            //if newOrOld value is new but there is no user in system should be send error massage
            model.addAttribute("message", "He he you tried to cheat us dear. \n please follow sign up procedure to create account.");
            return "user/register";
        }

        conformationTokenService.deleteByConformationToken(conformationToken);
        return "redirect:/login";
    }

}
/*
 URL url = new URL(request.getRequestURL().toString());
    String host  = url.getHost();
    String userInfo = url.getUserInfo();
    String scheme = url.getProtocol();
    String port = url.getPort();
    String path = request.getAttribute("javax.servlet.forward.request_uri");
    String query = request.getAttribute("javax.servlet.forward.query_string");

    URI uri = new URI(scheme,userInfo,host,port,path,query,null)

* */