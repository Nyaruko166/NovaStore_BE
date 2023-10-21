package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Cart;
import com.sd64.novastore.repository.CartRepository;
import com.sd64.novastore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;


    @Override
    public List<Cart> getAllCart() {
        {
            return cartRepository.findAll();
        }
    }

    @Override
    public Page<Cart> getAllCartPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return cartRepository.findAll(pageable);
    }

    @Override
    public Cart add(Cart cart) {
        cart.setCreateDate(new Date());
        return cartRepository.save(cart);
    }

    @Override
    public Cart update(Cart cart, Integer id) {
        Optional<Cart> optional = cartRepository.findById(id);
        if (optional.isPresent()) {
            Cart updateCart = optional.get();
            cart.setId(id);
            cart.setCreateDate(updateCart.getCreateDate());
            return cartRepository.save(cart);
        } else {
            return null;
        }
    }

    @Override
    public Cart delete(Integer id) {
        Optional<Cart> optional = cartRepository.findById(id);
        if (optional.isPresent()) {
            Cart cart = optional.get();
            return cartRepository.save(cart);
        } else {
            return null;
        }
    }

    @Override
    public Cart getOne(Integer id) {
        return cartRepository.findById(id).orElse(null);
    }

}
