package com.sd64.novastore.service;

import com.sd64.novastore.model.PaymentMethod;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> getAll();

    Page<PaymentMethod> getAll(Integer page);

    PaymentMethod add(PaymentMethod paymentMethod);

    PaymentMethod update(PaymentMethod paymentMethod, Integer id);

    PaymentMethod delete(Integer id);

    PaymentMethod getOne(Integer id);

    List<PaymentMethod> getAllBillPaymentMethod(Integer billId);
}
