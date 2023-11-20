package com.sd64.novastore.service;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public interface PaymentService {

    JsonObject vnpayCreate(HttpServletRequest req, Long price, String address) throws UnsupportedEncodingException;

    JsonObject zalopayCreate(Long amount, String address) throws IOException, URISyntaxException;

    JsonObject MomoPayCreate(Long amount, String address) throws IOException, URISyntaxException;
}
