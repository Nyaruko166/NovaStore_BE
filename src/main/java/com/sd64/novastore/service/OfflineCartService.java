/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.service;

import com.sd64.novastore.model.OfflineCart;
import com.sd64.novastore.model.ProductDetail;

import java.util.List;
import java.util.Map;

public interface OfflineCartService {

    void addToCart(String codeCtsp, Integer qty);

    List<OfflineCart> getCart();

    void deleteCart(String codeCtsp);

    void emptyCart(Map<String, Integer> empty);
}
