package com.sd64.novastore.repository;

import com.sd64.novastore.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<Account> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<Account> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);
}
