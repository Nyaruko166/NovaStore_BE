package com.sd64.novastore.repository;

import com.sd64.novastore.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiamGiaRepository extends JpaRepository<ProductDetail,Integer> {
    List<ProductDetail> findByProductId(Integer productId);
}
