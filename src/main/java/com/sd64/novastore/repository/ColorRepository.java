package com.sd64.novastore.repository;

import com.sd64.novastore.model.Color;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    List<Color> findAllByStatus(Integer status);

    Page<Color> findAllByStatus(Pageable pageable, Integer status);
}
