package com.sd64.novastore.repository;

import com.sd64.novastore.model.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, Integer> {
}
