package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.Voucher;
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
        return promotionRepository.findAllByStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Promotion> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionRepository.findAllByStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Promotion add(Promotion promotion) {
        promotion.setCreateDate(new Date());
        promotion.setUpdateDate(new Date());
        promotion.setStatus(1);
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion update(Promotion promotion, Integer id) {
        Optional<Promotion> optional = promotionRepository.findById(id);
        if (optional.isPresent()){
            Promotion oldPromotion = optional.get();
            promotion.setId(oldPromotion.getId());
            promotion.setCreateDate(oldPromotion.getCreateDate());
            promotion.setUpdateDate(new Date());
            promotion.setStatus(oldPromotion.getStatus());
            return promotionRepository.save(promotion);
        } else {
            return null;
        }
    }

    @Override
    public Promotion delete(Integer id) {
        Optional<Promotion> optional = promotionRepository.findById(id);
        if (optional.isPresent()){
            Promotion promotion = optional.get();
            promotion.setStatus(0);
            return promotionRepository.save(promotion);
        } else {
            return null;
        }
    }

    @Override
    public Promotion getOne(Integer id) {
        return promotionRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Promotion> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
