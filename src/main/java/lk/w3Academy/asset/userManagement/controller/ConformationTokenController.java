package lk.w3Academy.asset.userManagement.controller;


import lk.w3Academy.asset.userManagement.entity.ConformationToken;
import lk.w3Academy.asset.userManagement.entity.Enum.ConformationTokenType;
import lk.w3Academy.asset.userManagement.entity.User;
import lk.w3Academy.asset.userManagement.service.ConformationTokenService;
import lk.w3Academy.asset.userManagement.service.UserService;
import lk.w3Academy.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/register")
public class ConformationTokenController {
    private final ConformationTokenService conformationTokenService;
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public ConformationTokenController(ConformationTokenService conformationTokenService, EmailService emailService, UserService userService) {
        this.conformationTokenService = conformationTokenService;
        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping
    public String registrationFrom() {
        return "user/registrationEmail";
    }

    @PostMapping
    public String tokenGenerate(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        ConformationToken newUserToken = new ConformationToken(email);
        ConformationToken passwordResetToken = new ConformationToken().passwordResetToken(email);
        //ConformationToken conformationToken = conformationTokenService.findByEmail(email);
        System.out.println("new user tokent "+ newUserToken.getToken());
        System.out.println("pass " +passwordResetToken.getToken());
        /*  if (conformationToken != null && conformationToken.getEndDate().isBefore(conformationToken.getCreateDate()) ){
            System.out.println("tokent is expired");
        }
//check user is already there or not
        User user = userService.findByUserName(email);
        if (user != null) {

        }
*/
        return "redirect:/home";
    }

    @GetMapping("/token/{token}")
    public String tokenSearch(@PathVariable String token) {
        //System.out.println("token " + conformationTokenService.findByToken(token));
        return "user/passwordEnter";
    }
}
