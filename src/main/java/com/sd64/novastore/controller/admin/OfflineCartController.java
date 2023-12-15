/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.controller.admin;

import com.google.gson.Gson;
import com.sd64.novastore.model.OfflineCartView;
import com.sd64.novastore.model.TempBill;
import com.sd64.novastore.service.OfflineCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/nova/pos")
public class OfflineCartController {

    @Autowired
    private OfflineCartService offlineCartService;

    AtomicInteger seq = new AtomicInteger();

    @GetMapping()
    public String cart(Model model, HttpSession session,
                       @RequestParam(value = "billId", defaultValue = "0") Integer billId) {
//        offlineCartService.addToCart("1","CT33",1);
        TempBill tempBill = offlineCartService.getBillById(billId);
        List<OfflineCartView> lstCart = offlineCartService.getCart(tempBill.getLstDetailProduct());
        List<TempBill> lstBill = offlineCartService.getLstBill();
//        System.out.println("lst members: \n");
//        for (TempBill x : lstBill) {
//            System.out.println(x.toString());
//        }
        session.setAttribute("posBill", tempBill);
        model.addAttribute("lstCart", lstCart);
        model.addAttribute("lstBill", lstBill);
        model.addAttribute("tempBill", tempBill);
        return "/admin/cart/offline-cart";
    }

    @PostMapping("/frag")
    public String frag(Model model, HttpSession session) {
        TempBill tempBill = (TempBill) session.getAttribute("tempBill");
        List<OfflineCartView> lstCart = offlineCartService.getCart(tempBill.getLstDetailProduct());
        model.addAttribute("lstCart", lstCart);
        return "/admin/cart/offline-cart-fragment :: frag";
    }

    @GetMapping("/remove/{code}")
    public String removeFromCart(@PathVariable("code") String data) {
        System.out.println(offlineCartService.deleteCart(data));
        return "redirect:/nova/pos";
    }

    @GetMapping("/newBill")
    public String newBill() {
        Integer id = seq.incrementAndGet();
        offlineCartService.addToLstBill(TempBill.builder()
                .billId(id).lstDetailProduct(new ArrayList<>()).build());

//        System.out.println("Tạo hoá đơn mới?");

        return "redirect:/nova/pos?billId=" + id;
    }

}
