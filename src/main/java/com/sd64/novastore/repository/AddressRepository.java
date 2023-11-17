package com.sd64.novastore.repository;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
//    @Query(value = "SELECT AD.Id ,AC.Id'AccountId',AD.SpecificAddress,AD.Ward,AD.District,AD.City,AD.CreateDate,AD.UpdateDate,AD.Status\n" +
//            "from  Account AC join Address AD on AD.AccountId= AC.Id", nativeQuery = true)
    List<Address> findAllByStatus(Integer status);

    Page<Address> findAllByStatus(Pageable pageable, Integer status);

    @Query("SELECT a FROM Address a WHERE a.status = 1 AND a.account.id = :accountId")
    Address findAccountDefaultAddress(@Param("accountId") Integer accountId);

    @Query("SELECT a FROM Address a WHERE a.status = 2 AND a.account.id = :accountId")
    List<Address> findAccountAddress(@Param("accountId") Integer accountId);
}
