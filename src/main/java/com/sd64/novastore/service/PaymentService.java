package com.sd64.novastore.service;

import com.sd64.novastore.dto.PaymentDto;
import com.sd64.novastore.model.Address;
import com.sd64.novastore.model.Cart;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public interface PaymentService {

    PaymentDto vnpayCreate(HttpServletRequest req, Long price, Cart cart, Address address) throws UnsupportedEncodingException;

    PaymentDto zalopayCreate(Long amount, Cart cart, Address address) throws IOException, URISyntaxException;

    PaymentDto MomoPayCreate(Long amount, Cart cart, Address address) throws IOException, URISyntaxException;
}
