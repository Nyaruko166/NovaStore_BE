package com.sd64.novastore.service;

import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.request.PromotionRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionService {

    List<Promotion> getAll();

    Page<Promotion> getPage(int page);

    Promotion add(PromotionRequest promotionRequest);

    Promotion update(PromotionRequest promotionRequest, Integer id);

    Boolean delete(Integer id);

    Page<Promotion> search(String name, int page);
}
