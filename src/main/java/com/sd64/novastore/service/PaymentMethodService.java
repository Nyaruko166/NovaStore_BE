package com.sd64.novastore.service;

import com.sd64.novastore.dto.PaymentMethodRequest;
import com.sd64.novastore.model.PaymentMethod;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> getAll();

    Page<PaymentMethod> getAll(Integer page);

    PaymentMethod add(PaymentMethodRequest paymentMethodRequest);

    PaymentMethod update(PaymentMethodRequest paymentMethodRequest, Integer id);

    Boolean delete(Integer id);
}
