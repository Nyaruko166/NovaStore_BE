package com.sd64.novastore.repository;

import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.PromotionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, Integer> {
    List<PromotionDetail> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<PromotionDetail> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);
    Page<PromotionDetail> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);
}
