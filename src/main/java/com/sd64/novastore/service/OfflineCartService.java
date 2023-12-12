/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.service;

import com.sd64.novastore.model.OfflineCart;
import com.sd64.novastore.model.OfflineCartView;

import java.util.List;
import java.util.Map;

public interface OfflineCartService {

    String addToCart(String billId, String detailProductId, Integer qty);

    List<OfflineCartView> getCart();

    void deleteCart(String codeCtsp);

    void emptyCart(List<OfflineCart> empty);
}
