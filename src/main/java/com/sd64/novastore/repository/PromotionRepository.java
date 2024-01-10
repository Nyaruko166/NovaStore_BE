package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.PromotionDetailDTO;
import com.sd64.novastore.dto.admin.thongke.PromotionSearchDTO;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    Optional<Promotion> findByCode(String code);

    Promotion findTopByOrderByIdDesc();

    Promotion findByName(String name);

    List<Promotion> findByEndDateBeforeAndStatus(Date endDate, Integer status);

    List<Promotion> findAllByStatusOrderByIdDesc(Integer status);

    @Query("SELECT p FROM Promotion p WHERE p.status IN (1, 2) ORDER BY p.id DESC")
    Page<Promotion> findAllByStatusOrderByIdDesc(Pageable pageable);

    Page<Promotion> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    @Query(value = "SELECT p.id AS id, p.code AS code, p.name AS name, p.value AS value, p.startDate AS startDate, p.endDate AS endDate, p.status AS status " +
            "FROM Promotion p"
    )
    Page<PromotionSearchDTO> listPromotions(Pageable pageable);

    @Query(value = "SELECT p.id AS id, p.code AS code, p.name AS name, p.value AS value, p.startDate AS startDate, p.endDate AS endDate, p.status AS status " +
            "FROM Promotion p " +
            "WHERE (p.code IS NULL OR p.code LIKE CONCAT('%', :code, '%')) " +
            "AND (:ngayTaoStart IS NULL OR :ngayTaoEnd IS NULL OR (p.endDate BETWEEN :ngayTaoStart AND :ngayTaoEnd)) " +
            "AND (p.status IS NULL OR p.status = :status) " +
            "AND (p.name IS NULL OR p.name LIKE CONCAT('%', :name, '%')) ")
    Page<PromotionSearchDTO> searchPromotion(
            @Param("code") String code,
            @Param("ngayTaoStart") Date ngayTaoStart,
            @Param("ngayTaoEnd") Date ngayTaoEnd,
            @Param("status") Integer status,
            @Param("name") String name,
            Pageable pageable);

    @Query(value = "SELECT " +
            "    PD.id AS promotionDetailId, " +
            "    P.id AS productId, " +
            "    PR.name AS promotionName, " +
            "    P.name AS productName, " +
            "    P.code AS productCode, " +
            "    MIN(PD2.price) AS minProductPrice, " +
            "    MAX(PD2.price) AS maxProductPrice, " +
            "    MIN(PD2.priceDiscount) AS minProductPriceDiscount, " +
            "    MAX(PD2.priceDiscount) AS maxProductPriceDiscount ," +
            "    PR.startDate AS promotionStartDate, " +
            "    PR.endDate AS promotionEndDate, " +
            "    PR.value AS promotionValue " +
            "FROM " +
            "    PromotionDetail PD " +
            "JOIN " +
            "    Product P ON PD.product.id = P.id " +
            "JOIN " +
            "    Promotion PR ON PD.promotion.id = PR.id " +
            "JOIN " +
            "    ProductDetail PD2 ON P.id = PD2.product.id " +
            "WHERE " +
            "    PD.promotion.id = :promotionId " +
            "    AND PD.status = 1 " +
            "    AND P.status = 2 " +
            "GROUP BY " +
            "    PD.id, PR.name, P.name, P.code, PR.startDate, PR.endDate, PR.value, P.id,P.createDate"+
            " ORDER BY P.createDate DESC"
    )
    List<PromotionDetailDTO> getPromotionDetailsByPromotionId(@Param("promotionId") Integer promotionId);

    Promotion findByNameAndStatus(String name, Integer status);
}
