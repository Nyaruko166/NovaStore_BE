package com.sd64.novastore.repository;

import com.sd64.novastore.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Page<Material> findAllByStatusOrderByUpdateDateDesc(Pageable pageable, Integer status);
    Page<Material> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    List<Material> findAllByStatusOrderByUpdateDateDesc(Integer status);

    Material findByName(String name);

    Optional<Material> findByCode(String code);

    Material findTopByOrderByIdDesc();
}
