package com.sd64.novastore.repository;

import com.sd64.novastore.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAllByStatus(Integer status);

    Page<Role> findAllByStatus(Pageable pageable, Integer status);
}
