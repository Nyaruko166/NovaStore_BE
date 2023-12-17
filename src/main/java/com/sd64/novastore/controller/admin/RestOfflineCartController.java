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
        List<OfflineCart> cart = offlineCartService.addToCart(response.getBillId(), response.getData(), 1);
        BigDecimal total = offlineCartService.calCartPrice(offlineCartService.getCart(cart));
        offlineCartService.addToLstBill(TempBill.builder().billId(response.getBillId())
                .totalCartPrice(total).lstDetailProduct(cart).build());
        TempBill tempBill = offlineCartService.getBillById(response.getBillId());
//        System.out.println(tempBill.getTotalCartPrice());
        session.setAttribute("posBill", tempBill);
//        System.out.println(offlineCartService.addToCart(response.getData(), 1));
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/customerNumber")
//    public List<CustomerResponse> getCustomer() {
//        List<CustomerResponse> lstCst = new ArrayList<>();
//        List<Account> lstAcc = accountService.getAll();
//        for (Account x : lstAcc) {
//            lstCst.add(CustomerResponse.builder()
//                    .customerEmail(x.getEmail())
//                    .customerName(x.getName())
//                    .id(x.getId())
//                    .phoneNumber(x.getPhoneNumber())
//                    .build());
//        }
//        return lstCst;
//    }

}
