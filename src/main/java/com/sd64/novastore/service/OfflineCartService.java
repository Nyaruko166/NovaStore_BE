/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.service;

import com.sd64.novastore.model.OfflineCart;
import com.sd64.novastore.model.OfflineCartView;
import com.sd64.novastore.model.TempBill;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

public interface OfflineCartService {

    BigDecimal calCartPrice(List<OfflineCartView> lstCart);

    List<OfflineCart> addToCart(Integer billId, String detailProductId, Integer qty);

    List<OfflineCartView> getCart(List<OfflineCart> cart);

    String deleteCart(String codeCtsp, Integer billId);

    void emptyCart(List<OfflineCart> empty);

    List<TempBill> getLstBill();

    Boolean addToLstBill(TempBill tempBill);

    Boolean removeFromLstBill(TempBill tempBill);

    TempBill getBillById(Integer id);

    TempBill checkout(TempBill tempBill);

    String genBillCode();

    List<OfflineCart> updateCart(Integer billId, String codeCTSP, Integer qty);

    Boolean checkSlTon(List<OfflineCart> lstCart);
}
