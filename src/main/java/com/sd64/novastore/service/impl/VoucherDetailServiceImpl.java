package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.VoucherDetail;
import com.sd64.novastore.repository.VoucherDetailRepository;
import com.sd64.novastore.service.VoucherDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherDetailServiceImpl implements VoucherDetailService {
    @Autowired
    private VoucherDetailRepository voucherDetailRepository;

    @Override
    public List<VoucherDetail> getAllVoucherDetail() {
        return voucherDetailRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<VoucherDetail> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherDetailRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public VoucherDetail add(VoucherDetail voucherDetail) {
        voucherDetail.setStatus(1);
        voucherDetail.setCreateDate(new java.util.Date());
        voucherDetail.setUpdateDate(new java.util.Date());
        return voucherDetailRepository.save(voucherDetail);
    }

    @Override
    public VoucherDetail update(VoucherDetail voucherDetail, Integer id) {
        Optional<VoucherDetail> voucherDetailOptional = voucherDetailRepository.findById(id);
        if (voucherDetailOptional.isPresent()) {
            VoucherDetail updateVoucherDetail = voucherDetailOptional.get();
            voucherDetail.setId(updateVoucherDetail.getId());
            voucherDetail.setStatus(updateVoucherDetail.getStatus());
            voucherDetail.setCreateDate(updateVoucherDetail.getCreateDate());
            voucherDetail.setUpdateDate(new Date());
            return voucherDetailRepository.save(voucherDetail);
        }
        return null;
    }

    @Override
    public VoucherDetail delete(Integer id) {
        Optional<VoucherDetail> voucherDetailOptional = voucherDetailRepository.findById(id);
        return voucherDetailOptional.map(promotionDetail -> {
            promotionDetail.setStatus(0);
            voucherDetailRepository.save(promotionDetail);
            return promotionDetail;
        }).orElse(null);
    }

    @Override
    public VoucherDetail getOne(Integer id) {
        return voucherDetailRepository.findById(id).orElse(null);
    }
}
