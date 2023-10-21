package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.repository.BillDetailRepository;
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

import java.awt.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

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
    public Bill delete(Integer id) {
        return null;
    }
}
