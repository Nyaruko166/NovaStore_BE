package com.sd64.novastore.controller;

import com.sd64.novastore.response.PaymentResponse;
import com.sd64.novastore.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/vnpay/create-payment")
    ResponseEntity<?> vnpayCreate(HttpServletRequest req) throws UnsupportedEncodingException {

        return ResponseEntity.ok(paymentService.vnpayCreate(req, 1000000L));
    }

    @GetMapping("/zalo/create-payment")
    ResponseEntity<?> zalopayCreate() throws IOException, URISyntaxException {

        return ResponseEntity.ok(paymentService.zalopayCreate());
    }

    @GetMapping("/vnpay/return")
    ResponseEntity<?> vnpayReturn(PaymentResponse paymentResponse) {

        return ResponseEntity.ok(paymentResponse);
    }
}
