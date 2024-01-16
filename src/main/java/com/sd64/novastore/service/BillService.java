package com.sd64.novastore.service;

import com.sd64.novastore.dto.admin.BillDto;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.SessionCart;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BillService {
    List<Bill> getAllBill();

    Page<Bill> getAllBillPT(Integer page);

    Bill addBillPos(Bill bill);

    Bill updateBill(Bill bill, Integer id);

    List<BillDetail> getAllBillDetail();

    Page<BillDetail> getAllBillDetailPT(Integer page);

    BillDetail addBillDetailPos(BillDetail billDetail);

    BillDetail updateBillDetail(BillDetail billDetail, Integer id);

    Bill getOneBill(Integer id);

    BillDetail getOneBillDetail(Integer id);

    List<BillDetail> getLstDetailByBillId(Integer id);

    Bill delete(Integer id);

    Bill placeOrder(Cart cart, String name, String specificAddress, String ward, String district, String city, String phoneNumber, String payment, Integer voucher);

    Bill placeOrderSession(SessionCart cart, String email, String name, String specificAddress, String ward, String district, String city, String phoneNumber, String payment, Integer voucher);

    List<Bill> getStatusOrders(Integer status, Integer customerId);

    List<Bill> getAllOrders(Integer customerId);

    boolean userCancelOrder(Integer billId);

    boolean confirmOrder(Integer billId, Account account);

    boolean cancelOrder(Integer billId, Account account);

    boolean shippingOrder(Integer id, Account account);

    boolean completeOrder(Integer id, Account account);

    boolean changeAddressOrder(Integer billId, String name, String phoneNumber, String specificAddress, String ward, String district, String city, Account account);

    boolean updateBillItem(Integer itemId, Integer quantity, Account account);

    boolean deleteBillItem(Integer itemId, Account account);

    boolean addBillItem(Integer billId, Integer productDetailId, Integer quantity, Account account);

    Page<BillDto> findAll(Pageable pageable);

    Page<BillDto> searchListBill(String code,
                                 Date ngayTaoStart,
                                 Date ngayTaoEnd,
                                 Integer status,
                                 Integer type,
                                 String phoneNumber, String customerName, Pageable pageable);

    String generateBillCode();

    void exportToExcel(HttpServletResponse response, Page<BillDto> bills, String exportUrl) throws IOException;
}
