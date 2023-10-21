package com.sd64.novastore.repository;

import com.sd64.novastore.model.BillDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {
    List<BillDetail> findAllByStatus(Integer status);

    List<BillDetail> findAllByBill_Id(Integer id);

    Page<BillDetail> findAllByStatus(Pageable pageable, Integer status);
}
