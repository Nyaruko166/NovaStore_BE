package com.sd64.novastore.service;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface PaymentService {

    String vnpayCreate(HttpServletRequest req) throws UnsupportedEncodingException;

}
