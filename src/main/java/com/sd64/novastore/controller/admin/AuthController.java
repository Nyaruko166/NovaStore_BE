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

    MailConfig mailConfig = new MailConfig();

    @GetMapping("/login")
    public String login() {
        return "/common/index";
    }

    @GetMapping("/access-denied")
    public String c403() {
        return "/common/access-denied";
    }

    @GetMapping("/forgot")
    public String forgor(@RequestParam(value = "email", defaultValue = "") String email,
                         HttpServletRequest request, Model model) {
        if (email.isBlank()) {
            return "forgor";
        }
        if (authService.codeGen(email, request)) {
            model.addAttribute("mess", "Đã gửi mã xác minh đến email của bạn...");
//            System.out.println("Mail đang được gửi...");
            return "forgor";
        }
        model.addAttribute("err", "Email này chưa được đăng ký !!!");
        return "/common/forgor";
    }

    @PostMapping("/reset-pass")
    public String forgor123(@RequestParam("email") String email,
                            @RequestParam("code") String code,
                            @RequestParam("newPassword") String newPassword,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request, Model model
    ) {
        Integer status = authService.codeVerifyAndNewPass(email, newPassword, code, request);
        if (status == 0) {
            model.addAttribute("mess", "Reset mật khẩu thành công");
            System.out.println("Reset mật khẩu thành công");
        } else if (status == 1) {
            model.addAttribute("err", "Mã xác minh đã hết hạn");
            System.out.println("Mã xác minh đã hết hạn");
        } else {
            model.addAttribute("err", "Mã xác minh không khớp");
            System.out.println("Mã xác minh không khớp");
        }
        return "/common/forgor";
    }

    @GetMapping("/register")
    public String register() {

        return "/common/registerFuckU";
    }

    @GetMapping("/change-password")
    public String changePass(@RequestParam(value = "email", defaultValue = "") String email,
                             HttpServletRequest request, Model model) {
        if (email.isBlank()) {
            return "change-pass";
        }
        if (authService.codeGenChangePass(email, request)) {
            model.addAttribute("mess", "Đã gửi mã xác minh đến email của bạn...");
            return "change-pass";
        }
        model.addAttribute("err", "Email này chưa được đăng ký !!!");
        return "change-pass";
    }

    @PostMapping("/change-pass-post")
    public String changePassPost(@RequestParam("email") String email,
                                 @RequestParam("code") String code,
                                 @RequestParam("currentPass") String currentPass,
                                 @RequestParam("newPass") String newPass,
                                 @RequestParam("reNewPass") String reNewPass,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request, Model model) {
        switch (authService.codeVerifyAndChangePass(email, currentPass, reNewPass, newPass, code, request)) {
            case 0:
                model.addAttribute("mess", "Đổi mật khẩu thành công !!!");
                break;
            case 1:
                model.addAttribute("err", "Tài khoản không tồn tại !!!");
                break;
            case 2:
                model.addAttribute("err", "Mật khẩu mới không khớp !!!");
                break;
            case 3:
                model.addAttribute("err", "Mật khẩu hiện tại không khớp !!!");
                break;
            case 4:
                model.addAttribute("err", "Mã xác minh hết hạn !!!");
                break;
            case 5:
                model.addAttribute("err", "Mã xác minh không khớp !!!");
                break;
        }
        return "change-pass";
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
