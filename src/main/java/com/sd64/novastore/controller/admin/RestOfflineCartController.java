/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.OfflineCart;
import com.sd64.novastore.model.OfflineCartView;
import com.sd64.novastore.model.TempBill;
import com.sd64.novastore.response.CustomerResponse;
import com.sd64.novastore.response.QrRespone;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.service.OfflineCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nova/pos")
public class RestOfflineCartController {

    @Autowired
    private OfflineCartService offlineCartService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody QrRespone response, HttpSession session) {
//        System.out.println(response.getBillId());
        List<OfflineCart> cart = offlineCartService.addToCart(response.getBillId(), response.getData(), response.getQty());
        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
        BigDecimal total = offlineCartService.calCartPrice(offlineCartService.getCart(cart));
        TempBill tempBill1 = (TempBill) session.getAttribute("posBill");
        tempBill1.setTotalCartPrice(total);
        tempBill1.setLstDetailProduct(cart);
//        offlineCartService.addToLstBill(TempBill.builder().billId(response.getBillId())
//                .totalCartPrice(total).lstDetailProduct(cart).build());
        offlineCartService.addToLstBill(tempBill1);
        TempBill tempBill = offlineCartService.getBillById(response.getBillId());
//        System.out.println(tempBill.getTotalCartPrice());
        session.setAttribute("posBill", tempBill);
//        System.out.println(offlineCartService.addToCart(response.getData(), 1));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody QrRespone response, HttpSession session) {

        List<OfflineCart> cart = offlineCartService.updateCart(response.getBillId(), response.getData(), response.getQty());
        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
        BigDecimal total = offlineCartService.calCartPrice(offlineCartService.getCart(cart));
        TempBill tempBill1 = (TempBill) session.getAttribute("posBill");
        tempBill1.setTotalCartPrice(total);
        tempBill1.setLstDetailProduct(cart);
        offlineCartService.addToLstBill(tempBill1);
        TempBill tempBill = offlineCartService.getBillById(response.getBillId());
        session.setAttribute("posBill", tempBill);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/check-legit")
    public ResponseEntity<?> checkLegit(HttpSession session) {
        TempBill tempBill = (TempBill) session.getAttribute("posBill");
        List<OfflineCart> lstCheck = tempBill.getLstDetailProduct();
        if (lstCheck.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
