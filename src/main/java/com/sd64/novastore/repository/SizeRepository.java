package com.sd64.novastore.repository;

import com.sd64.novastore.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

    List<Size> findAllByStatus(Integer status);

    Page<Size> findAllByStatus(Pageable pageable, Integer status);
}
