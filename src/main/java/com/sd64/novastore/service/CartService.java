package com.sd64.novastore.service;

import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.model.ProductDetail;

public interface CartService {
    Cart addToCart(ProductDetail productDetail, Integer quantity, String email);

    Cart getCart(String email);
}
