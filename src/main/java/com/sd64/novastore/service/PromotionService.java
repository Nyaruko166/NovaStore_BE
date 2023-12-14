package com.sd64.novastore.service;

import com.sd64.novastore.model.Promotion;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionService {
    void scheduleDeleteExpiredPromotions();

    List<Promotion> getExpiredPromotions();

    void processExpiredPromotion(Promotion promotion);

    List<Promotion> getAll();

    Page<Promotion> getAllPT(Integer page);

    Promotion add(Promotion promotion);

    Promotion update(Promotion promotion, Integer id);

    Promotion delete(Integer id);

    Promotion getOne(Integer id);

    Page<Promotion> search(String name, int page);
}
