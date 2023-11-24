package com.sd64.novastore.service;

import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.SessionCart;

public interface CartService {
    Cart addToCart(ProductDetail productDetail, Integer quantity, String email);

    Cart updateCart(ProductDetail productDetail, Integer quantity, String email);

    Cart removeFromCart(ProductDetail productDetail, String email);

    SessionCart addToCartSession(SessionCart sessionCart, ProductDetail productDetail, Integer quantity);

    SessionCart updateCartSession(SessionCart sessionCart, ProductDetail productDetail, Integer quantity);

    SessionCart removeFromCartSession(SessionCart sessionCart, ProductDetail productDetail);

    Cart combineCart(SessionCart sessionCart, String email);

    Cart getCart(String email);

    void deleteCartById(Integer id);
}
