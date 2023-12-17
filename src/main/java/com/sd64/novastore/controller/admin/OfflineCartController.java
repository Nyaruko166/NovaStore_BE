/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.controller.admin;

import com.google.gson.Gson;
import com.sd64.novastore.model.*;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.OfflineCartService;
import com.sd64.novastore.service.ProductDetailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private BillService billService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductDetailService productDetailService;

    AtomicInteger seq = new AtomicInteger();

    @GetMapping()
    public String cart(Model model, HttpSession session,
                       @RequestParam(value = "billId", defaultValue = "0") Integer billId) {
        List<TempBill> lstBill = offlineCartService.getLstBill();
        if (lstBill.isEmpty()) {
            lstBill.add(TempBill.builder().billId(0).billCode(offlineCartService.genBillCode())
                    .lstDetailProduct(new ArrayList<>()).build());
        }
        TempBill tempBill = offlineCartService.getBillById(billId);
        if (tempBill == null) {
            return "redirect:/nova/pos";
        }
        List<OfflineCartView> lstCart = offlineCartService.getCart(tempBill.getLstDetailProduct());
        tempBill.setTotalCartPrice(offlineCartService.calCartPrice(lstCart));
        session.setAttribute("posBill", tempBill);
        model.addAttribute("lstCart", lstCart);
        model.addAttribute("lstBill", lstBill);
        model.addAttribute("tempBill", tempBill);
//        for (TempBill x : lstBill) {
//            System.out.println(x.toString());
//        }
        return "/admin/cart/offline-cart";
    }

    @GetMapping("/remove/{code}")
    public String removeFromCart(@PathVariable("code") String data, HttpSession session) {
        TempBill tempBill = getSession(session);
        System.out.println(offlineCartService.deleteCart(data, tempBill.getBillId()));
        return "redirect:/nova/pos?billId=" + tempBill.getBillId();
    }

    @GetMapping("/newBill")
    public String newBill() {
        Integer id = seq.incrementAndGet();
        offlineCartService.addToLstBill(TempBill.builder()
                .billId(id).billCode(offlineCartService.genBillCode()).lstDetailProduct(new ArrayList<>()).build());
        return "redirect:/nova/pos?billId=" + id;
    }

    @GetMapping("/removeBill")
    public String removeBill(HttpSession session) {
        TempBill tempBill = getSession(session);
        offlineCartService.removeFromLstBill(tempBill);
        return "redirect:/nova/pos";
    }

    @GetMapping("/addCustomer/{id}")
    public String addCustomer(@PathVariable("id") Integer id, HttpSession session) {
        TempBill tempBill = getSession(session);
        Account account = accountService.findOne(id);
        System.out.println(tempBill.getBillCode());
        tempBill.setIdCustomer(account.getId());
        tempBill.setCustomerEmail(account.getEmail());
        tempBill.setCustomerPhone(account.getPhoneNumber());
        tempBill.setCustomerName(account.getName());
        offlineCartService.addToLstBill(tempBill);
        session.setAttribute("posBill", tempBill);
        return "redirect:/nova/pos?billId=" + tempBill.getBillId();
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        TempBill tempBill = getSession(session);
        offlineCartService.checkout(tempBill);
        return "redirect:/nova/pos";
    }

    @PostMapping("/api/productFilter")
    public String searchProducts(@RequestParam("keyword") String keywordProduct,
                                 Model model) {
        Pageable pageable = Pageable.ofSize(10);
        if (keywordProduct.isBlank()) {
            model.addAttribute("lstPro", null);
            return "/admin/cart/offline-cart-fragment :: product_frag";
        }
        List<ProductDetail> lstPro = productDetailService.findAllByProductNameAndStatus(keywordProduct, 1, pageable).getContent();
        model.addAttribute("lstPro", lstPro);
        model.addAttribute("keywordProduct", keywordProduct);

        return "/admin/cart/offline-cart-fragment :: product_frag";
    }

    @PostMapping("/api/filter")
    public String searchCustomers(@RequestParam("keyword") String keyword, Model model) {
        Pageable pageable = Pageable.ofSize(10);
//        System.out.println(accountService.searchCustomer(keyword, pageable).toString());
        if (keyword.isBlank()) {
            model.addAttribute("lstCus", null);
            return "/admin/cart/offline-cart-fragment :: modal_frag";
        }
        model.addAttribute("lstCus", accountService.searchCustomer(keyword, pageable).getContent());
        model.addAttribute("keyword", keyword);
        return "/admin/cart/offline-cart-fragment :: modal_frag";
    }

    @PostMapping("/frag")
    public String frag(Model model, HttpSession session) {
        TempBill tempBill = getSession(session);
        List<OfflineCartView> lstCart = offlineCartService.getCart(tempBill.getLstDetailProduct());
        model.addAttribute("lstCart", lstCart);
        return "/admin/cart/offline-cart-fragment :: frag";
    }

    @PostMapping("/frag-checkout")
    public String replaceCheck(Model model, HttpSession session) {
        TempBill tempBill = getSession(session);
        List<OfflineCartView> lstCart = offlineCartService.getCart(tempBill.getLstDetailProduct());
        model.addAttribute("tempBill", tempBill);
        return "/admin/cart/summary-frag :: replace";
    }

    public TempBill getSession(HttpSession session) {
        return (TempBill) session.getAttribute("posBill");
    }

}
