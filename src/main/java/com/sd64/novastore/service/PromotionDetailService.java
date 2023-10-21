package com.sd64.novastore.service;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.PromotionDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionDetailService {
    List<PromotionDetail> getAllPromotionDetail();

    Page<PromotionDetail> getAllPT(Integer page);

    PromotionDetail add(PromotionDetail promotionDetail);

    PromotionDetail update(PromotionDetail promotionDetail, Integer id);

    PromotionDetail delete(Integer id);

    PromotionDetail getOne(Integer id);

}
