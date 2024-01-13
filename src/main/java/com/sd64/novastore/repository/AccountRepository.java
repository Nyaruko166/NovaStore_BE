package com.sd64.novastore.repository;

import com.sd64.novastore.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT c FROM Account c WHERE c.email LIKE %:keyword% OR c.name LIKE %:keyword% OR c.phoneNumber LIKE %:keyword%")
    Page<Account> searchAccountKeyword(String keyword, Pageable pageable);

    Account findFirstByEmail(String email);

    List<Account> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<Account> findAllByAndStatusOrderByUpdateDateDesc(Pageable pageable, Integer status);

    Page<Account> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    Account findByPhoneNumberAndStatus(String phoneNumnber, Integer status);

    Account findByEmailAndStatus(String email, Integer status);

    @Query(value = "SELECT a " +
            "FROM Account a WHERE (a.name LIKE %:name% OR :name IS NULL) " +
            "AND (a.email LIKE %:email% OR :email IS NULL) " +
            "AND (a.birthday =:birthday OR :birthday IS NULL) " +
            "AND (a.phoneNumber LIKE %:phoneNumber% OR :phoneNumber IS NULL) " +
            "AND (a.role.id =:roleId OR :roleId IS NULL) " +
            "AND a.status IN (:status) ORDER BY a.updateDate DESC ")
    Page<Account> searchAccount(String name, Date birthday, String email, String phoneNumber, Integer roleId, Integer status, Pageable pageable);

}
