package com.sd64.novastore.repository;

import com.sd64.novastore.model.PromotionDetail;
import com.sd64.novastore.model.VoucherDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherDetailRepository extends JpaRepository<VoucherDetail, Integer> {
    List<VoucherDetail> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<VoucherDetail> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);
}
