package com.sd64.novastore.service;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public interface PaymentService {

    String vnpayCreate(HttpServletRequest req, Long price) throws UnsupportedEncodingException;

    String zalopayCreate() throws IOException, URISyntaxException;

    String MomoPayCreate() throws IOException, URISyntaxException;
}
