package com.sd64.novastore.repository;

import com.sd64.novastore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByStatus(Integer status);

    Page<Product> findAllByStatus(Pageable pageable, Integer status);
}
