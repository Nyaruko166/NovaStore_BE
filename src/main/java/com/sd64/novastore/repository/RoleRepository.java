package com.sd64.novastore.repository;

import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findFirstByName(String name);

    List<Role> findAllByAndStatusOrderByUpdateDateDesc(Integer status);

    Page<Role> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<Role> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);
}
