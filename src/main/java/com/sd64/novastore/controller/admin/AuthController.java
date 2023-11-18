package com.sd64.novastore.controller.admin;

import com.sd64.novastore.config.MailConfig;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.service.AuthService;
import com.sd64.novastore.utils.MailUtil;
import com.sd64.novastore.utils.TempCodeManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping()
public class AuthController {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthService authService;

    TempCodeManager tempCodeManager = new TempCodeManager();

    MailConfig mailConfig = new MailConfig();

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String c403() {
        return "access-denied";
    }

    @GetMapping("/forgot")
    public String forgor(@RequestParam(value = "email", defaultValue = "") String email,
                         HttpServletRequest request) {
        if (email.isBlank()) {
            return "forgor";
        }
        if (authService.codeGen(email, request)) {
            System.out.println("Mail đang được gửi...");
            return "forgor";
        }
        System.out.println("vl lỗi");
        return "forgor";
    }

    @PostMapping("/reset-pass")
    public String forgor123(@RequestParam("email") String email,
                            @RequestParam("code") String code,
                            @RequestParam("newPassword") String newPassword,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request, Model model
    ) {
        if (authService.codeVerifyAndNewPass(email, newPassword, code, request)) {
            System.out.println("Ngon");
        } else {
            System.out.println("Khong Ngon");
        }
        return "access-denied";
    }

    @PostMapping("/forgot-post")
    public String forgorPost(@RequestParam("code") String code, HttpServletRequest request) {
        boolean check = tempCodeManager.verifyTemporaryCode(request.getRemoteAddr(), code);
        if (check) {

        }
        return "forgor";
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @GetMapping("/mail")
    public String test() {

        String confirmCode = String.valueOf(mailConfig.randomCode());

        String body = mailUtil.confirmMailTemplate
                ("occho1666@gmail.com", confirmCode, mailConfig.company, mailConfig.contact);

        mailUtil.sendEmail("occho1666@gmail.com", mailConfig.confirmMail, body);

        return "access-denied";
    }

}
