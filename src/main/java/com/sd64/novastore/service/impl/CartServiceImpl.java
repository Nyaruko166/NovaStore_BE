package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.CartRequest;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.repository.CartRepository;
import com.sd64.novastore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Page<Cart> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return cartRepository.findAll(pageable);
    }

    @Override
    public Cart add(CartRequest cartRequest) {
        Cart cart = cartRequest.map(new Cart());
        return cartRepository.save(cart);
    }

    @Override
    public Cart update(CartRequest cartRequest, Integer id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        return cartOptional.map(cart -> {
            cart.setCreateDate(Date.from(Instant.parse(cartRequest.getCreateDate())));
            cart.setAccount(Account.builder().id(Integer.valueOf(cartRequest.getAccountId())).build());
            return cartRepository.save(cart);
        }).orElse(null);
    }

    @Override
    public Cart delete(Integer id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        return cartOptional.map(cart -> {
            cartRepository.delete(cart);
            return cart;
        }).orElse(null);
    }
}
