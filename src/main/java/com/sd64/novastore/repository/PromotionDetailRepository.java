package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.Impl.PromotionDetailDTOImpl;
import com.sd64.novastore.dto.admin.PromotionDetailDTO;
import com.sd64.novastore.dto.admin.thongke.PromotionSearchDTO;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.PromotionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, Integer> {
    List<PromotionDetail> findByPromotionId(Integer promotionId);
    List<PromotionDetail> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<PromotionDetail> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);
    Boolean existsByProductIdAndStatus(Integer productId, Integer status);

    PromotionDetail findByProductIdAndStatus(Integer productId, Integer status);

    @Query(value = "SELECT PD.id AS promotionDetailId, PR.name AS promotionName, P.name AS productName, " +
            "P.code AS productCode, MIN(PD2.price) AS minProductPrice, MAX(PD2.price) AS maxProductPrice, " +
            "PR.startDate AS promotionStartDate, PR.endDate AS promotionEndDate, PR.value AS promotionValue " +
            "FROM PromotionDetail PD " +
            "JOIN Product P ON PD.product.id = P.id " +
            "JOIN Promotion PR ON PD.promotion.id = PR.id " +
            "JOIN ProductDetail PD2 ON P.id = PD2.product.id " +
            "GROUP BY PD.id, PR.name, P.name, P.code, PR.startDate, PR.endDate, PR.value")
    Page<PromotionDetailDTO> All(Pageable pageable);
}
