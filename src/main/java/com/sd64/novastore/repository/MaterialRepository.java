package com.sd64.novastore.repository;

import com.sd64.novastore.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    List<Material> findAllByStatus(Integer status);

    Page<Material> findAllByStatus(Pageable pageable, Integer status);
}
