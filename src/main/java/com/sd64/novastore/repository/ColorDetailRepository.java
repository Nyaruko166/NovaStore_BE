package com.sd64.novastore.repository;

import com.sd64.novastore.model.ColorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorDetailRepository extends JpaRepository<ColorDetail, Integer> {
}
