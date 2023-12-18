package com.sd64.novastore.controller.admin;

import com.sd64.novastore.config.MailConfig;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Address;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.service.AddressService;
import com.sd64.novastore.service.AuthService;
import com.sd64.novastore.utils.MailUtil;
import com.sd64.novastore.utils.TempCodeManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping()
public class AuthController {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthService authService;

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
            return "/common/forgor";
        }
        if (authService.codeGen(email, request)) {
            model.addAttribute("mess", "Đã gửi mã xác minh đến email của bạn...");
//            System.out.println("Mail đang được gửi...");
            return "/common/forgor";
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
            redirectAttributes.addFlashAttribute("mess", "Reset mật khẩu thành công");
            return "redirect:/login";
        } else if (status == 1) {
            redirectAttributes.addFlashAttribute("mess", "Mã xác minh đã hết hạn");
            System.out.println("Mã xác minh đã hết hạn");
        } else {
            redirectAttributes.addFlashAttribute("mess", "Mã xác minh không khớp");
            System.out.println("Mã xác minh không khớp");
        }
        return "redirect:/forgot";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Account account = new Account();
//        model.addAttribute("x", account);
        return "/common/register";
    }

    @PostMapping("/register-post")
    public String registerP(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("sdt") String sdt,
            @RequestParam("password") String password,
            @RequestParam("rePassword") String rePassword,
            RedirectAttributes redirectAttributes
    ) {

        Account user = Account.builder()
                .name(name)
                .email(email)
                .phoneNumber(sdt)
                .password(password)
                .build();

        if (accountService.registerUser(user) == 0) {
            redirectAttributes.addFlashAttribute("mess", "Đăng ký thành công !!");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("mess", "Email đã tồn tại !!");
//        redirectAttributes.addFlashAttribute("x", user);tttttt
        return "redirect:/register";
    }

    @GetMapping("/change-password")
    public String changePass(@RequestParam(value = "email", defaultValue = "") String email,
                             HttpServletRequest request, Model model) {
        if (email.isBlank()) {
            return "/common/change-pass";
        }
        if (authService.codeGenChangePass(email, request)) {
            model.addAttribute("mess", "Đã gửi mã xác minh đến email của bạn...");
            return "/common/change-pass";
        }
        model.addAttribute("mess", "Email này chưa được đăng ký !!!");
        return "/common/change-pass";
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
                redirectAttributes.addFlashAttribute("mess", "Đổi mật khẩu thành công !!!");
                return "redirect:/login";
            case 1:
                redirectAttributes.addFlashAttribute("mess", "Tài khoản không tồn tại !!!");
                break;
            case 2:
                redirectAttributes.addFlashAttribute("mess", "Mật khẩu mới không khớp !!!");
                break;
            case 3:
                redirectAttributes.addFlashAttribute("mess", "Mật khẩu hiện tại không khớp !!!");
                break;
            case 4:
                redirectAttributes.addFlashAttribute("mess", "Mã xác minh hết hạn !!!");
                break;
            case 5:
                redirectAttributes.addFlashAttribute("mess", "Mã xác minh không khớp !!!");
                break;
        }
        return "redirect:/change-password";
    }

    @GetMapping("/profile")
    public String p5(Model model, Principal principal) {

        Account currentLog = getCurrentUser(principal);
        List<Address> lstAddress = addressService.findAccountAddress(currentLog.getId());
        Address defaultAddress = addressService.findAccountDefaultAddress(currentLog.getId());
        model.addAttribute("user", currentLog);
        model.addAttribute("defaultAddress", defaultAddress);
        model.addAttribute("lstAddress", lstAddress);

        return "/common/profile";
    }

    @PostMapping("/user/address")
    public String addAddress(Model model, Address address, Principal principal) {
        Address defaultAddress = addressService.findAccountDefaultAddress(getCurrentUser(principal).getId());
        if (defaultAddress == null) {
            address.setStatus(1);
        } else {
            address.setStatus(2);
        }
        Account account = accountService.findOne(getCurrentUser(principal).getId());
        address.setAccount(account);
        addressService.add(address);
        return "redirect:/profile";
    }

    @GetMapping("/user/makeDefault/{id}")
    public String makeDef(Model model, Principal principal,
                          @PathVariable("id") Integer id) {

        Address defaultAddress = addressService.findAccountDefaultAddress(getCurrentUser(principal).getId());
        defaultAddress.setStatus(2);
        addressService.update(defaultAddress, defaultAddress.getId());

        Address newDefault = addressService.getOne(id);
        newDefault.setStatus(1);
        addressService.update(newDefault, newDefault.getId());

        return "redirect:/profile";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteAddress(Model model, Principal principal,
                                @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Address address = addressService.getOne(id);
        if (address.getStatus() == 1) {
            redirectAttributes.addFlashAttribute("err", "Không thể xoá địa chỉ mặc định!!");
            return "redirect:/profile";
        }
        addressService.delete(address.getId());
        return "redirect:/profile";
    }

    private Account getCurrentUser(Principal principal) {
        return accountService.findFirstByEmail(principal.getName());
    }

}
