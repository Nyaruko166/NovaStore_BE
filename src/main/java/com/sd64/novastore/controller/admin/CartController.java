package com.sd64.novastore.controller.admin;


import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private AccountService accountService;
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Cart cart = cartService.getOne(id);
        List<Account> accountList = accountService.getAll();
        model.addAttribute("cart", cart);
//        model.addAttribute("accountList", accountList);
        return "/admin/cart/cart-detail";
    }

    @GetMapping("/page")
    public String getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Cart> page1 = cartService.getAllCartPT(page);
        model.addAttribute("page", page1);
        return "/admin/cart/cart";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        cartService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/admin/cart/page";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("CartDetail") Cart cart, RedirectAttributes redirectAttributes) {
        cartService.add(cart);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/admin/cart/page";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("CartDetail") Cart cart, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        cartService.update(cart, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/admin/cart/page";

    }

}
