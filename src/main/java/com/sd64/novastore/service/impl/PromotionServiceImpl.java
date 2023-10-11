package com.sd64.novastore.service.impl;

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

    public List<Promotion> getAll() {
        return promotionRepository.findAllByStatus(1);
    }

    public Page<Promotion> getPage(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionRepository.findAllByStatus(pageable, 1);
    }

    public Promotion add(PromotionRequest promotionRequest) {
        Promotion promotion = promotionRequest.map(new Promotion());
        return promotionRepository.save(promotion);
    }

    public Promotion update(PromotionRequest promotionRequest, Integer id) {
        Optional<Promotion> optional = promotionRepository.findById(id);
        return optional.map(o -> {
            o.setId(id);
            o.setName(promotionRequest.getName());
            o.setType(promotionRequest.getType());
            o.setStatus(promotionRequest.getStatus());
            o.setUpdateDate(new Date());
            o.setCode(promotionRequest.getCode());
            o.setValue(promotionRequest.getValue());
            o.setEndDate(promotionRequest.getEndDate());
            o.setStartDate(promotionRequest.getStartDate());
            o.setCreateDate(promotionRequest.getCreateDate());
            return promotionRepository.save(o);
        }).orElse(null);
    }


    public Boolean delete(Integer id) {
        Optional<Promotion> optional = promotionRepository.findById(id);
        return optional.map(o -> {
            o.setStatus(0);
            promotionRepository.save(o);
            return true;
        }).orElse(false);
    }
}
