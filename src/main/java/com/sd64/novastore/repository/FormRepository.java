package com.sd64.novastore.repository;

import com.sd64.novastore.model.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {
    Page<Form> findAllByStatusOrderByUpdateDateDesc(Pageable pageable, Integer status);
    List<Form> findAllByStatusOrderByUpdateDateDesc(Integer status);
    Page<Form> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    Form findByName(String name);
}
