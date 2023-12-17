package com.sd64.novastore.service;

import com.sd64.novastore.dto.admin.Impl.PromotionDetailDTOImpl;
import com.sd64.novastore.dto.admin.PromotionDetailDTO;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.PromotionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
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

    List<ProductDetail> getAllPrDT();

    List<Product> getProductsByIds(List<Integer> productIds);

    Boolean existsByProductIdAndStatus(Integer productId, Integer status);

    Page<PromotionDetailDTO> All(Integer page);
}
