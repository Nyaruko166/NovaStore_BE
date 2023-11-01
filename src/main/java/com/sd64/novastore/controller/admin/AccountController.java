package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.service.RoleService;
import com.sd64.novastore.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/nova/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

//    @GetMapping("/all")
//    public String getAll() {
//        return ;
//    }

    @GetMapping("/page")
    public String getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Account> pageAccount = accountService.getAllPT(page);
        model.addAttribute("pageAccount", pageAccount.getContent());
        model.addAttribute("lstRole", roleService.getAll());
//        System.out.println(SecurityUtil.getSessionUser());
        return "admin/account/account";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Account account = accountService.findOne(id);
        model.addAttribute("account", account);
        model.addAttribute("lstRole", roleService.getAll());
        return "/admin/account/account-detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        accountService.delete(id);
        return "redirect:/nova/account/page";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("Account") Account account,
                      @RequestParam("avt") MultipartFile avt,
                      @RequestParam("roleId") Integer roleId,
                      RedirectAttributes redirectAttributes) {
        accountService.add(account, avt, roleId);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/nova/account/page";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("Account") Account account,
                         @RequestParam("avt") MultipartFile avt,
                         @RequestParam("roleId") Integer roleId,
                         @PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        accountService.update(account, avt, roleId, id);
        redirectAttributes.addFlashAttribute("mess", "Update thành công!!");
        return "redirect:/nova/account/page";
    }

//    @GetMapping("/search")
//    public String search(@RequestParam String name, @RequestParam(defaultValue = "0") int page) {
//        return ResponseEntity.ok(accountService.search(name, page).getContent());
//    }
}
