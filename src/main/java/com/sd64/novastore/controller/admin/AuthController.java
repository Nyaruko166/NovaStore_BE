package com.sd64.novastore.controller.admin;

import com.sd64.novastore.config.MailConfig;
import com.sd64.novastore.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class AuthController {

    @Autowired
    private MailUtil mailUtil;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String c403() {
        return "access-denied";
    }

    @GetMapping("/mail")
    public String test() {

        MailConfig mailConfig = new MailConfig();

        String confirmCode = String.valueOf(mailConfig.randomCode());

        String body = mailUtil.confirmMailTemplate
                ("occho1666@gmail.com", confirmCode, mailConfig.company, mailConfig.contact);

        mailUtil.sendEmail("occho1666@gmail.com", mailConfig.confirmMail, body);

        return "access-denied";
    }

}
