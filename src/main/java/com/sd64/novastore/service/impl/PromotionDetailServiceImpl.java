package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.PromotionDetail;
import com.sd64.novastore.repository.PromotionDetailRepository;
import com.sd64.novastore.service.PromotionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionDetailServiceImpl implements PromotionDetailService {
    @Autowired
    private PromotionDetailRepository promotionDetailRepository;

    @Override
    public List<PromotionDetail> getAllPromotionDetail() {
        return promotionDetailRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<PromotionDetail> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionDetailRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public PromotionDetail add(PromotionDetail promotionDetail) {
        promotionDetail.setStatus(1);
        promotionDetail.setCreateDate(new java.util.Date());
        promotionDetail.setUpdateDate(new java.util.Date());
        return promotionDetailRepository.save(promotionDetail);
    }

    @Override
    public PromotionDetail update(PromotionDetail promotionDetail, Integer id) {
        Optional<PromotionDetail> promotionDetailOptional = promotionDetailRepository.findById(id);
        if (promotionDetailOptional.isPresent()) {
            PromotionDetail updatePromotionDetail = promotionDetailOptional.get();
            promotionDetail.setId(updatePromotionDetail.getId());
            promotionDetail.setStatus(updatePromotionDetail.getStatus());
            promotionDetail.setCreateDate(updatePromotionDetail.getCreateDate());
            promotionDetail.setUpdateDate(new Date());
            return promotionDetailRepository.save(promotionDetail);
        }
        return null;
    }

    @Override
    public PromotionDetail delete(Integer id) {
        Optional<PromotionDetail> promotionDetailOptional = promotionDetailRepository.findById(id);
        return promotionDetailOptional.map(promotionDetail -> {
            promotionDetail.setStatus(0);
            promotionDetailRepository.save(promotionDetail);
            return promotionDetail;
        }).orElse(null);
    }

    @Override
    public PromotionDetail getOne(Integer id) {
        return promotionDetailRepository.findById(id).orElse(null);
    }

}
