package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Brand;
import com.sd64.novastore.request.AccountRequest;
import com.sd64.novastore.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/admin/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

//    @GetMapping("/all")
//    public String getAll() {
//        return ;
//    }

    @GetMapping("/page")
    public String getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Account> pageAccount = accountService.getAllPT(page);
        model.addAttribute("pageAccount", pageAccount.getContent());
        return "admin/account/account";
    }

    @PutMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        return "";
    }


    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Account") Account account, RedirectAttributes mess) {
        accountService.add(account);
        mess.addFlashAttribute("mess","Thêm thành công!!");
        return "redirect:/admin/account/page";
    }

    @PutMapping("/update/{id}")
    public String update(@RequestBody @Valid AccountRequest accountRequest, @PathVariable Integer id, BindingResult result) {
        return "";

    }

//    @GetMapping("/search")
//    public String search(@RequestParam String name, @RequestParam(defaultValue = "0") int page) {
//        return ResponseEntity.ok(accountService.search(name, page).getContent());
//    }
}
