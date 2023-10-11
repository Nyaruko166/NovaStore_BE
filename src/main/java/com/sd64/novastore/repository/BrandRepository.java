package com.sd64.novastore.repository;

import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findAllByStatus(Integer status);

    Page<Brand> findAllByStatus(Pageable pageable, Integer status);
}
