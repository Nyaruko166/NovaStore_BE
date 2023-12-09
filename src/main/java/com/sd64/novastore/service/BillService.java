package com.sd64.novastore.service;

import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.SessionCart;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface BillService {
    List<Bill> getAllBill();

    Page<Bill> getAllBillPT(Integer page);

    Bill addBill(Bill bill);

    Bill updateBill(Bill bill, Integer id);

    List<BillDetail> getAllBillDetail();

    Page<BillDetail> getAllBillDetailPT(Integer page);

    BillDetail addBillDetail(BillDetail billDetail);

    BillDetail updateBillDetail(BillDetail billDetail, Integer id);

    Bill getOneBill(Integer id);

    BillDetail getOneBillDetail(Integer id);

    List<BillDetail> getLstDetailByBillId(Integer id);

    Bill delete(Integer id);

    Bill placeOrder(Cart cart, String name, String address, String phoneNumber, String payment);

    Bill placeOrderSession(SessionCart cart, String email, String name, String address, String phoneNumber, String payment);

    List<Bill> getNoConfirmOrders(Integer customerId);

    List<Bill> getAllOrders(Integer customerId);

    Bill cancelOrder(Integer billId);

    Bill acceptBill(Integer billId);

    Bill shippingOrder(Integer id, BigDecimal shippingFee);
}
