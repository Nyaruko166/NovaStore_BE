/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OfflineCartController {

    @GetMapping("/test")
    public String cart(){

        return "/admin/cart/offline-cart";
    }

}
