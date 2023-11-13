package com.sd64.novastore.repository;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {
    List<Form> findAllByAndStatusOrderByIdDesc(Integer status);

//    Page<Form> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<Form> findAllByStatusOrderByUpdateDateDesc(Pageable pageable, Integer status);
    Page<Form> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    Form findByName(String name);
}
