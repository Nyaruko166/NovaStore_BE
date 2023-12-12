/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.OfflineCart;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.repository.OfflineCartRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.service.OfflineCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OfflineCartServiceImpl implements OfflineCartService {

    @Autowired
    private OfflineCartRepository repository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public void addToCart(String codeCtsp, Integer qty) {
        Map<String, Integer> cart = repository.getCartSP();
        if (cart.containsKey(codeCtsp)) {
            Integer slHienTai = cart.get(codeCtsp);
            cart.put(codeCtsp, slHienTai + qty);
        } else {
            cart.put(codeCtsp, qty);
        }
    }

    @Override
    public List<OfflineCart> getCart() {
        List<OfflineCart> lstCart = new ArrayList<>();
        List<ProductDetail> lstCtsp = productDetailRepository.findAll();
        Map<String, Integer> cart = repository.getCartSP();
        for (Map.Entry<String, Integer> x : cart.entrySet()) {
            for (ProductDetail y : lstCtsp) {
                if (y.getCode().equals(x.getKey())) {

                    lstCart.add(OfflineCart.builder()
                            .name(y.getProduct().getName())
                            .idCtsp(y.getId())
                            .codeCtsp(y.getCode())
                            .qty(x.getValue())
                            .price(y.getPrice())
                            .color(y.getColor().getName())
                            .size(y.getSize().getName())
                            .build());
                }
            }
        }
        return lstCart;
    }

    @Override
    public void deleteCart(String codeCtsp) {
        Map<String, Integer> cart = repository.getCartSP();
        cart.remove(codeCtsp);
    }

    @Override
    public void emptyCart(Map<String, Integer> empty) {
        repository.setCartSP(empty);
    }
}
