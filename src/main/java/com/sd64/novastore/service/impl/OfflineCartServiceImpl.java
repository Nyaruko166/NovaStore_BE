/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.*;
import com.sd64.novastore.repository.ImageRepository;
import com.sd64.novastore.repository.OfflineCartRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.service.OfflineCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OfflineCartServiceImpl implements OfflineCartService {

    @Autowired
    private OfflineCartRepository repository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    //    List<OfflineCart> testCart = new ArrayList<>();

    @Override
    public List<OfflineCart> addToCart(Integer billId, String detailProductId, Integer qty) {
        List<OfflineCart> cart = getBillById(billId).getLstDetailProduct();
        for (OfflineCart x : cart) {
            if (x.getDetailProductId().equals(detailProductId)) {
                Integer slHienTai = x.getQty();
                x.setQty(slHienTai + qty);
//                System.out.println("update cart");
                return cart;
            }
        }
        cart.add(new OfflineCart(detailProductId, qty));
//        System.out.println("thêm mới vào cart");
        return cart;
    }

    @Override
    public List<OfflineCartView> getCart(List<OfflineCart> cart) {
        List<OfflineCartView> lstCart = new ArrayList<>();
        List<ProductDetail> lstCtsp = productDetailRepository.findAll();
        for (OfflineCart x : cart) {
            for (ProductDetail y : lstCtsp) {
                if (y.getCode().equals(x.getDetailProductId())) {
                    lstCart.add(OfflineCartView.builder()
                            .name(y.getProduct().getName())
                            .idSP(y.getProduct().getId())
                            .idCtsp(y.getId())
                            .codeCtsp(y.getCode())
                            .qty(x.getQty())
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
    public String deleteCart(String codeCtsp, Integer billId) {
        List<OfflineCart> cart = getBillById(billId).getLstDetailProduct();
        for (OfflineCart x : cart) {
            if (x.getDetailProductId().equals(codeCtsp)) {
                cart.remove(x);
                return "Xoá Thành Công!";
            }
        }
        return "Xoá Thất Bại!";
    }

    @Override
    public void emptyCart(List<OfflineCart> empty) {
        repository.setCartSP(empty);
    }

    @Override
    public List<TempBill> getLstBill() {
        return repository.getLstTempBill();
    }

    @Override
    public Boolean addToLstBill(TempBill tempBill) {
        List<TempBill> lstBill = repository.getLstTempBill();
        for (TempBill x : lstBill) {
            if (Objects.equals(x.getBillId(), tempBill.getBillId())) {
                int index = lstBill.indexOf(x);
                lstBill.set(index, tempBill);
//                System.out.println("đã có, update");
                return true;
            }
        }
        lstBill.add(tempBill);
//        System.out.println("thêm mới");
        return true;
    }

    @Override
    public Boolean removeFromLstBill(TempBill tempBill) {
        List<TempBill> lstBill = repository.getLstTempBill();
        for (TempBill x : lstBill) {
            if (x.getBillId().equals(tempBill.getBillId())) {
                lstBill.remove(x);
                return true;
            }
        }
        return false;
    }

    @Override
    public TempBill getBillById(Integer id) {
        List<TempBill> lstBill = repository.getLstTempBill();
        for (TempBill x : lstBill) {
            if (Objects.equals(x.getBillId(), id)) {
                return x;
            }
        }
        return null;
    }

    @Override
    public BigDecimal calCartPrice(List<OfflineCartView> lstCart) {
        BigDecimal total = BigDecimal.valueOf(0);
        for (OfflineCartView x : lstCart) {
            total = total.add(x.calTotalPrice());
        }
        return total;
    }
}
