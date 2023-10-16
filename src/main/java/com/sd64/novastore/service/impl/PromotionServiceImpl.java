package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.repository.PromotionRepository;
import com.sd64.novastore.request.PromotionRequest;
import com.sd64.novastore.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<Promotion> getAll() {
        return promotionRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Promotion> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Promotion add(Promotion promotion) {
        promotion.setStatus(1);
        promotion.setCreateDate(new java.util.Date());
        promotion.setUpdateDate(new java.util.Date());
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion update(Promotion promotion, Integer id) {
        Optional<Promotion> optional = promotionRepository.findById(id);
        if (optional.isPresent()) {
            Promotion updatePromotion = optional.get();
            promotion.setId(id);
            promotion.setName(updatePromotion.getName());
            promotion.setStatus(updatePromotion.getStatus());
            promotion.setCreateDate(updatePromotion.getCreateDate());
            promotion.setUpdateDate(new Date());
            return promotionRepository.save(promotion);
        } else {
            return null;
        }
    }

    @Override
    public Promotion delete(Integer id) {
        Optional<Promotion> optional = promotionRepository.findById(id);
        if (optional.isPresent()) {
            Promotion promotion = optional.get();
            promotion.setStatus(0);
            return promotionRepository.save(promotion);
        } else {
            return null;
        }
    }

    @Override
    public Page<Promotion> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
