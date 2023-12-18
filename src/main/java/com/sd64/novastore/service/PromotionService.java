package com.sd64.novastore.service;

import com.sd64.novastore.dto.admin.BillDto;
import com.sd64.novastore.dto.admin.PromotionDetailDTO;
import com.sd64.novastore.dto.admin.thongke.PromotionSearchDTO;
import com.sd64.novastore.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;
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

    Page<PromotionSearchDTO> findAll(Pageable pageable);

    Page<PromotionSearchDTO> searchPromotion(
            String code,
            Date ngayTaoStart,
            Date ngayTaoEnd,
            Integer status,
            String name,
            int page);
    List<PromotionDetailDTO> getPromotionDetailsByPromotionId(Integer promotionId);
}
