package com.sd64.novastore.repository;

import com.sd64.novastore.model.Size;
import com.sd64.novastore.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {

    List<Voucher> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<Voucher> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<Voucher> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);
}
