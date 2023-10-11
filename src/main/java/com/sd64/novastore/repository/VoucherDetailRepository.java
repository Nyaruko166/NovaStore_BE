package com.sd64.novastore.repository;

import com.sd64.novastore.model.VoucherDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherDetailRepository extends JpaRepository<VoucherDetail, Integer> {
}
