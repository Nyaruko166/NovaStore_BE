package com.sd64.novastore.repository;

import com.sd64.novastore.model.SizeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeDetailRepository extends JpaRepository<SizeDetail, Integer> {
}
