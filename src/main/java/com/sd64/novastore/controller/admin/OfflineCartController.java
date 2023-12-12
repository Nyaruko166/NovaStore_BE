/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.OfflineCart;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.service.OfflineCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/nova/pos")
public class OfflineCartController {

    @Autowired
    private OfflineCartService offlineCartService;

    @GetMapping()
    public String cart(Model model) {
        List<OfflineCart> lstCart = offlineCartService.getCart();
        model.addAttribute("lstCart", lstCart);
        return "/admin/cart/offline-cart";
    }

    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody String codeCtsp) {
        System.out.println(codeCtsp);
//        offlineCartService.addToCart(codeCtsp, 1);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/qr-scan")
    public String qr() {
        return "/admin/cart/qr-popup";
    }

}
