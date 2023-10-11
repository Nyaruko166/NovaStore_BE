package com.sd64.novastore.repository;

import com.sd64.novastore.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    List<ProductDetail> findAllByStatus(Integer status);

    Page<ProductDetail> findAllByStatus(Pageable pageable, Integer status);
}
