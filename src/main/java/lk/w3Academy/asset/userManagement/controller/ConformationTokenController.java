package lk.w3Academy.asset.userManagement.controller;

import lk.w3Academy.asset.userManagement.entity.ConformationToken;
import lk.w3Academy.asset.userManagement.entity.Enum.ConformationTokenType;
import lk.w3Academy.asset.userManagement.entity.Enum.UType;
import lk.w3Academy.asset.userManagement.entity.Role;
import lk.w3Academy.asset.userManagement.entity.User;
import lk.w3Academy.asset.userManagement.service.ConformationTokenService;
import lk.w3Academy.asset.userManagement.service.RoleService;
import lk.w3Academy.asset.userManagement.service.UserService;
import lk.w3Academy.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/register")
public class ConformationTokenController {
    private final ConformationTokenService conformationTokenService;
    private final EmailService emailService;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public ConformationTokenController(ConformationTokenService conformationTokenService, EmailService emailService, UserService userService, RoleService roleService) {
        this.conformationTokenService = conformationTokenService;
        this.emailService = emailService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = {"/new", "/forgotten"})
    public String registrationFrom(HttpServletRequest httpServletRequest, Model model) {
        String url = httpServletRequest.getRequestURI();
        System.out.println(url.contains("forgotten"));
        if (url.contains("forgotten")) {
            model.addAttribute("addState", true);
            model.addAttribute("message",
                    "Don't worry. Resetting your password is easy, just tell us the email address you registered with W3Accademy.");
        } else {
            model.addAttribute("addState", false);
            model.addAttribute("message", "Please enter valid email address. \n To send registration link to your email");
        }
        return "user/registrationEmail";
    }

    @PostMapping
    public String tokenGenerate(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        //email common subject
        String emailSubject = "Registration / Password Rest URL - (Not Reply)";
        //make url to this process
        String url = MvcUriComponentsBuilder
                .fromMethodName(ConformationTokenController.class, "tokenSearch", "")
                .toUriString();
        //before create a token need to find if there is a user on system
        User user = userService.findByUserName(email);
        //need to find there is a valid token on system
        ConformationToken conformationToken = conformationTokenService.findByEmail(email);
        // if conformation token is not null check it validity
        if (conformationToken != null && user == null) {
            if (conformationToken.getCreateDate().isBefore(conformationToken.getEndDate())) {
                emailService.sendEmail(email, emailSubject, url.concat(conformationToken.getToken()));
            }
            //if token is not valid token start and end date change
            else {
                conformationToken.setCreateDate(LocalDateTime.now());
                conformationToken.setEndDate(conformationToken.getCreateDate().plusDays(1));
                emailService.sendEmail(email, emailSubject, url.concat(conformationTokenService.persist(conformationToken).getToken()));
            }
            redirectAttributes.addFlashAttribute("tokenMessage", "Could you please check your email ?" + "\n Mail to email : " + email);
            return "redirect:/home";
        }
        //if there is user on system send forgotten password reset link
        else if (user != null) {
            // need to send password reset link
            emailService.sendEmail(email, emailSubject, url.concat(conformationTokenService.persist(new ConformationToken().passwordResetToken(email)).getToken()));
            redirectAttributes.addFlashAttribute("tokenMessage", "You already have account ! \n Password reset link send to your email" + email + "and follow it's instruction");
        } else {
            //create a new conformation token
            emailService.sendEmail(email, emailSubject, url.concat(conformationTokenService.persist(new ConformationToken(email)).getToken()));
            redirectAttributes.addFlashAttribute("tokenMessage", "Please check your email and follow it's instruction. \nMail to email : " + email);
        }
        return "redirect:/home";
    }

    @GetMapping("/token/{token}")
    public String tokenSearch(@PathVariable String token, Model model, RedirectAttributes redirectAttributes) {
        ConformationToken conformationToken = conformationTokenService.findByToken(token);
        // User user = userService.findByUserName(email);
        if (conformationToken != null && conformationToken.getCreateDate().isBefore(conformationToken.getEndDate())) {
            model.addAttribute("addState", conformationToken.getConformationTokenType().equals(ConformationTokenType.PASRESET));
            model.addAttribute("email", conformationToken.getEmail());
            model.addAttribute("message", "Please enter your new password");
            model.addAttribute("conformation", new ConformationToken());
            return "user/passwordEnter";
        } else {
            redirectAttributes.addFlashAttribute("message", "Token is expired please enter email again.");
            return "redirect:/register";
        }
    }

    @PostMapping("/token")
    public String makeUser(@Valid @ModelAttribute ConformationToken conformationToken) {
        User user = new User();
        user.setUsername(conformationToken.getEmail());
        user.setEnabled(true);
        user.setUType(UType.NONSPECIFIC);
        if (conformationToken.getPassword().equals(conformationToken.getNewPassword())) {
            user.setPassword(conformationToken.getPassword());
        }
        List<Role> roles = new ArrayList<>();
        for (Role role : roleService.findAll()) {
            if (role.getRoleName().contains("Student")) {
                roles.add(role);
                break;
            }
        }
        user.setRoles(roles);
        userService.persist(user);
        conformationTokenService.deleteByConformationToken(conformationToken);
        return "redirect:/login";
    }

    @PostMapping("/forgotten")
    public String passwordReset(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        User user = userService.findByUserName(email);
        if (user != null) {
            emailService.sendEmail(email, "Password reset token ", MvcUriComponentsBuilder
                    .fromMethodName(ConformationTokenController.class, "tokenSearch", "")
                    .toUriString()
                    .concat(conformationTokenService.persist(
                            new ConformationToken().passwordResetToken(email)).getToken()));
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("message", "There is no user according to you provided email. \n\t"+email);
            return "redirect:/register";
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