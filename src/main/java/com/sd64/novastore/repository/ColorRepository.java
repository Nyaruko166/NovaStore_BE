package com.sd64.novastore.repository;

import com.sd64.novastore.model.Color;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    List<Color> findAllByStatusOrderByUpdateDateDesc(Integer status);

    Page<Color> findAllByStatusOrderByUpdateDateDesc(Pageable pageable, Integer status);

    Page<Color> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    Optional<Color> findByCode(String code);

    Color findByName(String name);
}
