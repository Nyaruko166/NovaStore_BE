//package com.sd64.novastore.controller.admin;
//
//import com.sd64.novastore.response.MomoPaymentResponse;
//import com.sd64.novastore.response.VNPaymentResponse;
//import com.sd64.novastore.response.ZaloPaymentResponse;
//import com.sd64.novastore.service.PaymentService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URISyntaxException;
//
//@RestController
//@RequestMapping("/api/payment")
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @GetMapping("/momo/create-payment")
//    ResponseEntity<?> momoCreate() throws IOException, URISyntaxException {
//
//        return ResponseEntity.ok(paymentService.MomoPayCreate(100000L, "123").get("pay_url"));
//    }
//
////    @GetMapping("/vnpay/create-payment")
////    ResponseEntity<?> vnpayCreate(HttpServletRequest req) throws UnsupportedEncodingException {
////
////        return ResponseEntity.ok(paymentService.vnpayCreate(req, 1000000L));
////    }
//
//    @GetMapping("/zalo/create-payment")
//    ResponseEntity<?> zalopayCreate() throws IOException, URISyntaxException {
//
//        return ResponseEntity.ok(paymentService.zalopayCreate(10000L, "123").get("pay_url"));
//    }
//
////    @GetMapping("/vnpay/return")
////    ResponseEntity<?> vnpayReturn(VNPaymentResponse VNPaymentResponse) {
////
////        return ResponseEntity.ok(VNPaymentResponse);
////    }
//
//    @GetMapping("/zalo/return")
//    ResponseEntity<?> zalopayReturn(ZaloPaymentResponse zaloPaymentResponse) {
//
//        return ResponseEntity.ok(zaloPaymentResponse);
//    }
//
//    @GetMapping("/momo/return")
//    ResponseEntity<?> momoReturn(MomoPaymentResponse momoPaymentResponse) {
//
//        return ResponseEntity.ok(momoPaymentResponse);
//    }
//}
