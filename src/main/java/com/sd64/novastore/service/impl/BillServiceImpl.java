package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.model.PaymentMethod;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.repository.BillDetailRepository;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.repository.BillRepository;
import com.sd64.novastore.repository.PaymentMethodRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<Bill> getAllBill() {
        return billRepository.findAllByStatus(1);
    }

    @Override
    public Page<Bill> getAllBillPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return billRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public Bill addBill(Bill bill) {
        bill.setStatus(1);
        bill.setCreateDate(new Date());
        bill.setUpdateDate(new Date());
        return billRepository.save(bill);
    }

    @Override
    public Bill updateBill(Bill bill, Integer id) {
        Optional<Bill> optional = billRepository.findById(id);
        if (optional.isPresent()) {
            Bill oldBill = optional.get();
            bill.setId(oldBill.getId());
            bill.setStatus(oldBill.getStatus());
            bill.setCreateDate(oldBill.getCreateDate());
            bill.setUpdateDate(new Date());
            return billRepository.save(bill);
        }
        return null;
    }

    @Override
    public List<BillDetail> getAllBillDetail() {
        return billDetailRepository.findAllByStatus(1);
    }

    @Override
    public Page<BillDetail> getAllBillDetailPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return billDetailRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public BillDetail addBillDetail(BillDetail billDetail) {
        billDetail.setStatus(1);
        return billDetailRepository.save(billDetail);
    }

    @Override
    public BillDetail updateBillDetail(BillDetail billDetail, Integer id) {
        Optional<BillDetail> optional = billDetailRepository.findById(id);
        if (optional.isPresent()) {
            BillDetail oldBillDetail = optional.get();
            billDetail.setId(oldBillDetail.getId());
            return billDetailRepository.save(billDetail);
        }
        return null;
    }

    @Override
    public Bill getOneBill(Integer id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public BillDetail getOneBillDetail(Integer id) {
        return billDetailRepository.findById(id).orElse(null);
    }

    @Override
    public List<BillDetail> getLstDetailByBillId(Integer id) {
        return billDetailRepository.findAllByBill_Id(id);
    }

    @Override
    public Bill delete(Integer id) {
        return null;
    }

    @Override
    @Transactional
    public Bill placeOrder(Cart cart, String address, String payment) {
        Bill bill = new Bill();
        bill.setCustomerName(cart.getCustomer().getName());
        bill.setAddress(address);
        bill.setPhoneNumber(cart.getCustomer().getPhoneNumber());
        bill.setOrderDate(new Date());
        bill.setTotalPrice(cart.getTotalPrice());
        bill.setCreateDate(new Date());
        bill.setUpdateDate(new Date());
        bill.setStatus(10);
        bill.setCustomer(cart.getCustomer());

        List<BillDetail> billDetailList = new ArrayList<>();
        for (CartDetail item : cart.getCartDetails()){
            BillDetail billDetail = new BillDetail();
            billDetail.setBill(bill);
            billDetail.setProductDetail(item.getProductDetail());
            billDetail.setPrice(item.getPrice());
            billDetail.setQuantity(item.getQuantity());
            billDetail.setStatus(1);
            billDetailRepository.save(billDetail);
            billDetailList.add(billDetail);
            ProductDetail productDetail = productDetailRepository.findById(item.getProductDetail().getId()).orElse(null);
            productDetail.setQuantity(productDetail.getQuantity() - item.getQuantity());
            if (productDetail.getQuantity() == 0){
                productDetail.setStatus(0);
            }
            productDetailRepository.save(productDetail);
        }

        bill.setBillDetails(billDetailList);
        cartService.deleteCartById(cart.getId());
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setBill(bill);
        paymentMethod.setName(payment);
        paymentMethod.setMoney(bill.getTotalPrice());
        paymentMethod.setDescription(payment);
        paymentMethod.setStatus(1);
        paymentMethodRepository.save(paymentMethod);
        return billRepository.save(bill);
    }

    @Override
    public List<Bill> getNoConfirmOrders(Integer customerId) {
        return billRepository.getOrders(1, customerId);
    }

    @Override
    public List<Bill> getAllOrders(Integer customerId) {
        return billRepository.getAllOrders(customerId);
    }

    @Override
    @Transactional
    public Bill cancelOrder(Integer billId) {
        Bill bill = billRepository.findById(billId).orElse(null);
        bill.setStatus(0);
        List<BillDetail> billDetailList = bill.getBillDetails();
        for (BillDetail billDetail : billDetailList){
            billDetail.setStatus(0);
            billDetailRepository.save(billDetail);
            ProductDetail productDetail = productDetailRepository.findById(billDetail.getProductDetail().getId()).orElse(null);
            productDetail.setQuantity(productDetail.getQuantity() + billDetail.getQuantity());
            productDetail.setStatus(1);
            productDetailRepository.save(productDetail);
        }
        bill.setBillDetails(billDetailList);
        return billRepository.save(bill);
    }
}
