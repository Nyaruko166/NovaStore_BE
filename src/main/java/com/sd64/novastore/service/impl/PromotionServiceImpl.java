package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.PromotionDetail;
import com.sd64.novastore.repository.ProductRepository;
import com.sd64.novastore.repository.PromotionDetailRepository;
import com.sd64.novastore.repository.PromotionRepository;
import com.sd64.novastore.service.PromotionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PromotionDetailRepository promotionDetailRepository;


    @Override
    @Transactional
    @Scheduled(cron = "0 38 16 * * ?")
    public void scheduleDeleteExpiredPromotions() {
        List<Promotion> expiredPromotions = getExpiredPromotions();
        for (Promotion promotion : expiredPromotions) {
            processExpiredPromotion(promotion);
        }
    }

    @Override
    public List<Promotion> getExpiredPromotions() {
        Date currentDate = new Date();
        return promotionRepository.findByEndDateBeforeAndStatus(currentDate, 1);
    }

    @Override
    public void processExpiredPromotion(Promotion promotion) {
        List<PromotionDetail> promotionDetails = promotionDetailRepository.findByPromotionId(promotion.getId());


        promotionDetailRepository.deleteAll(promotionDetails);

        for (PromotionDetail promotionDetail : promotionDetails) {
            Product product = promotionDetail.getProduct();
            product.setStatus(1);
        }
        promotion.setStatus(0);

        try {
            promotionRepository.save(promotion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        if (optional.isPresent()) {
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
    @Transactional
    public Promotion delete(Integer id) {
        Optional<Promotion> optional = promotionRepository.findById(id);
        if (optional.isPresent()) {
            Promotion promotion = optional.get();
            List<PromotionDetail> promotionDetails = promotionDetailRepository.findByPromotionId(id);
            promotionDetailRepository.deleteAll(promotionDetails);
            for (PromotionDetail promotionDetail : promotionDetails) {
                Product product = promotionDetail.getProduct();
                product.setStatus(1);
            }
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
