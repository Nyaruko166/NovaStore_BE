/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.response.CustomerResponse;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.service.ProductDetailService;
import lombok.AllArgsConstructor;
import org.apache.xmlbeans.impl.soap.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nova/account")
public class RestAccountController {

    @Autowired
    private ProductDetailService productDetailService;

//    @PostMapping("/api/productFilter")
//    public ResponseEntity<?> searchProducts(@RequestParam("keyword") String keywordProduct,
//                                            Model model) {
//        Pageable pageable = Pageable.ofSize(10);
//        List<ProductDetail> lstPro = new ArrayList<>();
//        if (keywordProduct.isBlank()) {
//            model.addAttribute("lstPro", null);
//            return ResponseEntity.ok("Notabc");
//        }
//        lstPro = productDetailService.findAllByProductNameAndStatus(keywordProduct, 1, pageable).getContent();
//        model.addAttribute("lstPro", lstPro);
//        model.addAttribute("keywordProduct", keywordProduct);
//
//        return ResponseEntity.ok("abc");
//    }

}
