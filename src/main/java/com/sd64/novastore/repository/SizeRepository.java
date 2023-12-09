package com.sd64.novastore.repository;

import com.sd64.novastore.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

    List<Size> findAllByStatusOrderByUpdateDateDesc(Integer status);

    // Pagination
    Page<Size> findAllByStatusOrderByUpdateDateDesc(Pageable pageable, Integer status);

    //Search
    Page<Size> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    Optional<Size> findByName(String name);

}
