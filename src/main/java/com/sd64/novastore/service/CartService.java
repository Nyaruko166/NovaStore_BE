package com.sd64.novastore.service;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Cart;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {
    List<Cart> getAllCart();

    Page<Cart> getAllCartPT(Integer page);

    Cart add(Cart cart);

    Cart update(Cart cart, Integer id);

    Cart delete(Integer id);

//    Page<Cart> search(String name, int page);

    Cart getOne(Integer id);

}
