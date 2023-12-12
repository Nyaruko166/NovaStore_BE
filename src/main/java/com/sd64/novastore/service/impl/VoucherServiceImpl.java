package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Voucher;
import com.sd64.novastore.repository.VoucherRepository;
import com.sd64.novastore.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<Voucher> getAll() {
        return voucherRepository.findAllByStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Voucher> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.findAllByStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Voucher add(Voucher voucher) {
        voucher.setCreateDate(new Date());
        voucher.setUpdateDate(new Date());
        voucher.setStatus(1);
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher update(Voucher voucher, Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        if (optional.isPresent()){
            Voucher oldVoucher = optional.get();
            voucher.setId(oldVoucher.getId());
            voucher.setCreateDate(oldVoucher.getCreateDate());
            voucher.setUpdateDate(new Date());
            voucher.setStatus(oldVoucher.getStatus());
            return voucherRepository.save(voucher);
        } else {
            return null;
        }
    }

    @Override
    public Voucher delete(Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        if (optional.isPresent()){
            Voucher voucher = optional.get();
            voucher.setStatus(0);
            return voucherRepository.save(voucher);
        } else {
            return null;
        }
    }

    @Override
    public Voucher getOne(Integer id) {
        return voucherRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Voucher> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }

    @Override
    public List<Voucher> getVoucherByCartPrice(BigDecimal cartPrice) {
        return voucherRepository.getVoucherByCartPrice(cartPrice);
    }

    @Override
    public Voucher getVoucherById(Integer id) {
        return voucherRepository.findById(id).orElse(null);
    }
}
