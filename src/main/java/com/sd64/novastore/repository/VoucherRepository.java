package com.sd64.novastore.repository;

import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    List<Voucher> findAllByStatusOrderByIdDesc(Integer status);

    Page<Voucher> findAllByStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<Voucher> findAllByNameContainsAndStatusOrderByIdDesc(String name, Integer status, Pageable pageable);

    @Query("SELECT v FROM Voucher v WHERE v.status = 1 AND v.minimumPrice <= :cartPrice ORDER BY v.value DESC")
    List<Voucher> getVoucherByCartPrice(@Param("cartPrice") BigDecimal cartPrice);
}
