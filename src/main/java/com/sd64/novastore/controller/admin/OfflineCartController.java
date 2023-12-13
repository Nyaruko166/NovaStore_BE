/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.controller.admin;

import com.google.gson.Gson;
import com.sd64.novastore.model.OfflineCartView;
import com.sd64.novastore.service.OfflineCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/nova/pos")
public class OfflineCartController {

    @Autowired
    private OfflineCartService offlineCartService;

    Gson gson = new Gson();

    @GetMapping()
    public String cart(Model model) {
//        offlineCartService.addToCart("1","CT33",1);
        List<OfflineCartView> lstCart = offlineCartService.getCart();
        model.addAttribute("lstCart", lstCart);
        return "/admin/cart/offline-cart";
    }

    @PostMapping("/frag")
    public String frag(Model model) {
        List<OfflineCartView> lstCart = offlineCartService.getCart();
        model.addAttribute("lstCart", lstCart);
        return "/admin/cart/offline-cart-fragment :: frag";
    }

    @GetMapping("/remove/{code}")
    public String removeFromCart(@PathVariable("code") String data) {
        System.out.println(offlineCartService.deleteCart(data));
        return "redirect:/nova/pos";
    }

}
