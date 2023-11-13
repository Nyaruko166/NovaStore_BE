package com.sd64.novastore.repository;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    List<Promotion> findAllByStatusOrderByIdDesc(Integer status);

    Page<Promotion> findAllByStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<Promotion> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);
}
