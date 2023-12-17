package com.sd64.novastore.repository;

import com.sd64.novastore.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Page<Brand> findAllByStatusOrderByUpdateDateDesc(Pageable pageable, Integer status);

    List<Brand> findAllByStatusOrderByUpdateDateDesc(Integer status);

    Page<Brand> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    Brand findByNameAndStatus(String name, Integer status);

    Optional<Brand> findByCode(String code);

    Brand findTopByOrderByIdDesc();
}
