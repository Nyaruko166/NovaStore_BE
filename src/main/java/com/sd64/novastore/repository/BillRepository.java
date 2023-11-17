package com.sd64.novastore.repository;

import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findAllByStatus(Integer status);

    Page<Bill> findAllByStatus(Pageable pageable, Integer status);

    @Query("SELECT b FROM Bill b WHERE b.status = :status AND b.customer.id = :customerId")
    List<Bill> getOrders(@Param("status") Integer status, @Param("customerId") Integer customerId);

    @Query("SELECT b FROM Bill b WHERE b.customer.id = :customerId ORDER BY b.orderDate DESC")
    List<Bill> getAllOrders(@Param("customerId") Integer customerId);
}
