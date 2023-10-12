package com.sd64.novastore.controller;

import com.sd64.novastore.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/vnpay/create-payment")
    ResponseEntity<?> vnpayCreate(HttpServletRequest req) throws UnsupportedEncodingException {

        return ResponseEntity.ok(paymentService.vnpayCreate(req));
    }

//    @GetMapping("/vnpay/return")
//    ResponseEntity<?> vnpayReturn(
//            @RequestParam
//
//
//    ) {
//
//        return ResponseEntity.ok(null);
//    }
//    GetMapping
}
