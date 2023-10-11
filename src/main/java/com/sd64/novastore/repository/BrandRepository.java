package com.sd64.novastore.repository;

import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.Brand;
import com.sd64.novastore.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<Brand> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<Brand> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);
}
