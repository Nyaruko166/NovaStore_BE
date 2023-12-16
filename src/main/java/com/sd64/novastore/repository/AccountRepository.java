package com.sd64.novastore.repository;

import com.sd64.novastore.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT c FROM Account c WHERE c.email LIKE %:keyword% OR c.name LIKE %:keyword% OR c.phoneNumber LIKE %:keyword%")
    Page<Account> searchAccountKeyword(String keyword, Pageable pageable);

    Account findFirstByEmail(String email);

    List<Account> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<Account> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<Account> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);
}
