package com.sd64.novastore.repository;

import com.sd64.novastore.model.BillHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillHistoryRepository extends JpaRepository<BillHistory,Integer> {
    List<BillHistory> findAllByStatus(Integer status);

    Page<BillHistory> findAllByStatusOrderByUpdateDateDesc(Pageable pageable, Integer status);
}
