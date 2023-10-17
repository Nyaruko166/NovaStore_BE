package com.sd64.novastore.service.impl;

import com.sd64.novastore.request.BillRequest;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.repository.BillRepository;
import com.sd64.novastore.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;

    @Override

    public List<Bill> getAll() {
        return billRepository.findAllByStatus(1);
    }

    @Override
    public Page<Bill> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return billRepository.findAllByStatus(pageable,1);
    }

    @Override
    public Bill add(BillRequest billRequest) {
        Bill bill = billRequest.map(new Bill());
        return billRepository.save(bill);
    }

    @Override
    public Bill update(BillRequest billRequest, Integer id) {
        Optional<Bill> billOptional = billRepository.findById(id);
        return billOptional.map(bill -> {
            bill.setCustomerName(billRequest.getCustomerName());
            bill.setAddress(billRequest.getAddress());
            bill.setPhoneNumber(billRequest.getPhoneNumber());
            bill.setNote(billRequest.getNote());
            bill.setOrderDate(java.util.Date.from(Instant.parse(billRequest.getOrderDate())));
            bill.setConfirmationDate(java.util.Date.from(Instant.parse(billRequest.getConfirmationDate())));
            bill.setShippingDate(java.util.Date.from(Instant.parse(billRequest.getShippingDate())));
            bill.setReceivedDate(java.util.Date.from(Instant.parse(billRequest.getReceivedDate())));
            bill.setCompletionDate(java.util.Date.from(Instant.parse(billRequest.getCompletionDate())));
            bill.setPaymentDate(java.util.Date.from(Instant.parse(billRequest.getPaymentDate())));
            bill.setShippingFee(BigDecimal.valueOf(Long.parseLong(billRequest.getShippingFee())));
            bill.setTotalPrice(BigDecimal.valueOf(Long.parseLong(billRequest.getTotalPrice())));
            bill.setCreateDate(java.util.Date.from(Instant.parse(billRequest.getCreateDate())));
            bill.setUpdateDate(Date.from(Instant.parse(billRequest.getUpdateDate())));
            bill.setStatus(Integer.valueOf(billRequest.getStatus()));
            bill.setAccount(Account.builder().id(Integer.valueOf(billRequest.getAccountID())).build());
            bill.setCustomerAccount(Account.builder().id(Integer.valueOf(billRequest.getCustomerID())).build());
            return billRepository.save(bill);
        }).orElse(null);
    }

    @Override
    public Bill delete(Integer id) {
        Optional<Bill> billOptional = billRepository.findById(id);
        return billOptional.map(bill -> {
            bill.setStatus(0);
            billRepository.save(bill);
            return bill;
        }).orElse(null);
    }
}
