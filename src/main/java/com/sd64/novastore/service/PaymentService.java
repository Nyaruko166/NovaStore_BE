package com.sd64.novastore.service;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public interface PaymentService {

    JsonObject vnpayCreate(HttpServletRequest req, Long price, String specificAddress, String ward, String district, String city, String name, String phoneNumber, String email, String voucher) throws UnsupportedEncodingException;

    JsonObject zalopayCreate(Long amount, String specificAddress, String ward, String district, String city, String name, String phoneNumber, String email, String voucher) throws IOException, URISyntaxException;

    JsonObject MomoPayCreate(Long amount, String specificAddress, String ward, String district, String city, String name, String phoneNumber, String email, String voucher) throws IOException, URISyntaxException;
}
