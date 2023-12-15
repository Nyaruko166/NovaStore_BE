package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.thongke.PromotionSearchDTO;
import com.sd64.novastore.model.Brand;
import com.sd64.novastore.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    List<Promotion> findByEndDateBeforeAndStatus(Date endDate, Integer status);
    List<Promotion> findAllByStatusOrderByIdDesc(Integer status);
    @Query("SELECT p FROM Promotion p WHERE p.status IN (1, 2) ORDER BY p.id DESC")
    Page<Promotion> findAllByStatusOrderByIdDesc(Pageable pageable);

    Page<Promotion> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    @Query(value = "SELECT p.id AS id, p.code AS code, p.name AS name, p.value AS value, p.startDate AS startDate, p.endDate AS endDate, p.status AS status " +
            "FROM Promotion p " +
            "WHERE p.startDate BETWEEN :tungay AND :denngay OR p.endDate BETWEEN :tungay AND :denngay",
            countQuery = "SELECT COUNT(*) FROM Promotion p " +
                    "WHERE p.startDate BETWEEN :tungay AND :denngay OR p.endDate BETWEEN :tungay AND :denngay")
    Page<PromotionSearchDTO> getPromoTion(
            @Param("tungay") String tungay,
            @Param("denngay") String denngay,
            Pageable pageable);
}
