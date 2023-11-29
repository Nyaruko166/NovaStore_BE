package com.sd64.novastore.service;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.PromotionDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionDetailService {
    List<PromotionDetail> getAllPromotionDetail();

    void save(Product product);


    Page<PromotionDetail> getAllPT(Integer page);

    PromotionDetail add(PromotionDetail promotionDetail);

    PromotionDetail update(PromotionDetail promotionDetail, Integer id);

    PromotionDetail delete(Integer id);


    PromotionDetail getOne(Integer id);


    List<Product> getAll();

    List<Product> getProductsByIds(List<Integer> productIds);

    Boolean existsByProductIdAndStatus(Integer productId, Integer status);
}
