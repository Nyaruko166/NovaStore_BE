package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Voucher;
import com.sd64.novastore.repository.VoucherRepository;
import com.sd64.novastore.request.VoucherRequest;
import com.sd64.novastore.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<Voucher> getAll() {
        return voucherRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Voucher> getPage(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Voucher add(VoucherRequest voucherRequest) {
        Voucher voucher = voucherRequest.map(new Voucher());
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher update(VoucherRequest voucherRequest, Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        return optional.map(o -> {
            o.setId(id);
            o.setName(voucherRequest.getName());
            o.setType(voucherRequest.getType());
            o.setValue(voucherRequest.getValue());
            o.setStatus(voucherRequest.getStatus());
            o.setUpdateDate(new Date());
            o.setStartDate(voucherRequest.getStartDate());
            o.setEndDate(voucherRequest.getEndDate());
            o.setMinimumPrice(voucherRequest.getMinimumPrice());
            o.setQuantity(voucherRequest.getQuantity());
            o.setCode(voucherRequest.getCode());
            o.setMaximumDiscount(voucherRequest.getMaximumDiscount());
            o.setCreateDate(voucherRequest.getCreateDate());
            return voucherRepository.save(o);
        }).orElse(null);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        return optional.map(o -> {
            o.setStatus(0);
            voucherRepository.save(o);
            return true;
        }).orElse(false);
    }

    @Override
    public Page<Voucher> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
