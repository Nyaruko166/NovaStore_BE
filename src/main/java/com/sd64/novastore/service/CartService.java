package com.sd64.novastore.service;

import com.sd64.novastore.dto.CartRequest;
import com.sd64.novastore.model.Cart;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {
    List<Cart> getAll();

    Page<Cart> getAllPT(Integer page);

    Cart add(CartRequest cartRequest);

    Cart update(CartRequest cartRequest, Integer id);

    Cart delete(Integer id);
}
