package com.sd64.novastore.repository;

import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findAllByStatus(Integer status);

    Page<Bill> findAllByStatus(Pageable pageable, Integer status);
}
