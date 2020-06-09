package lk.w3Campus.asset.userManagement.controller;


import lk.w3Campus.asset.commonAsset.service.CommonService;
import lk.w3Campus.asset.userManagement.entity.ConformationToken;
import lk.w3Campus.asset.userManagement.entity.Role;
import lk.w3Campus.asset.userManagement.entity.User;
import lk.w3Campus.asset.userManagement.service.ConformationTokenService;
import lk.w3Campus.asset.userManagement.service.RoleService;
import lk.w3Campus.asset.userManagement.service.UserService;
import lk.w3Campus.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private String sendTokenToEmail(@RequestParam("email") String email, @RequestParam("newOrOld") String newOrOld, Model model, HttpServletRequest request) {
        //if not email
        if (!commonService.isValidEmail(email)) {
            model.addAttribute("message", "Please enter valid email.");
            return "user/register";
        }
        //check if there is any user on system
        User user = userService.findByUserName(email);
        //before create the token need to check there is user on current email
        // if not create token else send forgotten password form to fill
        if (user != null && newOrOld == null) {
            model.addAttribute("message", "There is an user on system please forgotten password reset option.");
            return "user/register";
        }
        //check if there is a valid token
        ConformationToken conformationToken = conformationTokenService.findByEmail(email);
        if (conformationToken != null && LocalDateTime.now().isBefore(conformationToken.getEndDate())) {
            System.out.println(" i here ");
            model.addAttribute("message", "There is valid token fot this email " + email + " on the system. \n Please check your email.");
            return "user/register";
        }

        ConformationToken newConformationToken = new ConformationToken(email);
        String url = request.getRequestURL().toString();
        emailService.sendEmail(email, "Email Verification (Police Recruitment Division) - Not reply",
                "Please click below link to active your account \n\t".concat(url + "/" + newOrOld + "/token/" + conformationTokenService.persist(newConformationToken).getToken()).concat("\n  this link is valid only one day. "));
        model.addAttribute("successMessage", "Please check your email \n Your entered email is \t ".concat(email));
        return "user/successMessage";
    }

    @GetMapping(value = {"/register/{newOrOld}/token/{token}"})
    public String passwordEnterPage(@PathVariable("newOrOld") String newOrOld, @PathVariable("token") String token, Model model) {
        // need to check token has valid or not
        //check if there is a valid token
        ConformationToken conformationToken = conformationTokenService.findByToken(token);
        if (conformationToken != null && LocalDateTime.now().isBefore(conformationToken.getEndDate())) {
            model.addAttribute("token", conformationToken.getToken());
            return "user/password";
        }
        conformationTokenService.deleteByConformationToken(conformationToken);
        model.addAttribute("message", "There is no valid token.");
        return "user/register";
    }

    @PostMapping("/register/user")
    public String newUser(@RequestParam("token") String token, @RequestParam("password") String password,
                          @RequestParam("reEnterPassword") String reEnterPassword, Model model) {
//token
        ConformationToken conformationToken = conformationTokenService.findByToken(token);
        if (conformationToken == null) {
            model.addAttribute("message", "Your token is not valid \n re-register.");
            return "redirect:/register";
        }
        if (!password.equals(reEnterPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("message", "Entered password is missed match \n please re enter.");
        }
        //taken user according to token email
        User userDB = userService.findByUserName(conformationToken.getEmail());
        //check password matched or not or not in user db

        if (userDB != null && password.equals(reEnterPassword)) {
            userDB.setPassword(password);
            userService.persist(userDB);
            conformationTokenService.deleteByConformationToken(conformationToken);
            return "redirect:/login";
        } else {
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
            conformationTokenService.deleteByConformationToken(conformationToken);
            return "redirect:/login";
        }
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