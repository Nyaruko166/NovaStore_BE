package com.sd64.novastore.repository;

import com.sd64.novastore.dto.ProductDto;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.PromotionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<Product> findAllByStatus(Pageable pageable, Integer status);

    @Query(value = "SELECT p.id as id, " +
            "p.name as name, " +
            "p.brand.name as brandName, " +
            "p.category.name as categoryName, " +
            "p.form.name as formName, " +
            "p.material.name as materialName " +
            " FROM Product p WHERE p.status = 1")
    Page<ProductDto> getAllProduct(Pageable pageable);
}
