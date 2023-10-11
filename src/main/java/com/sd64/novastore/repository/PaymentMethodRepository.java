package com.sd64.novastore.repository;

import com.sd64.novastore.model.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    List<PaymentMethod> findAllByStatus(Integer status);

    Page<PaymentMethod> findAllByStatus(Pageable pageable, Integer status);
}
