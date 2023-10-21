package com.sd64.novastore.service.impl;

import com.sd64.novastore.request.PaymentMethodRequest;
import com.sd64.novastore.model.PaymentMethod;
import com.sd64.novastore.repository.PaymentMethodRepository;
import com.sd64.novastore.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> getAll() {
        return paymentMethodRepository.findAllByStatusOrderByIdDesc(1);
    }

    @Override
    public Page<PaymentMethod> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return paymentMethodRepository.findAllByStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public PaymentMethod add(PaymentMethod paymentMethod) {
        paymentMethod.setStatus(1);
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod update(PaymentMethod paymentMethod, Integer id) {
        Optional<PaymentMethod> optional = paymentMethodRepository.findById(id);
        if (optional.isPresent()){
            PaymentMethod oldPaymentMethod = optional.get();
            paymentMethod.setId(oldPaymentMethod.getId());
            paymentMethod.setStatus(oldPaymentMethod.getStatus());
            return paymentMethodRepository.save(paymentMethod);
        } else {
            return null;
        }
    }

    @Override
    public PaymentMethod delete(Integer id) {
        Optional<PaymentMethod> optional = paymentMethodRepository.findById(id);
        if (optional.isPresent()){
            PaymentMethod paymentMethod = optional.get();
            paymentMethod.setStatus(0);
            return paymentMethodRepository.save(paymentMethod);
        } else {
            return null;
        }
    }

    @Override
    public PaymentMethod getOne(Integer id) {
        return paymentMethodRepository.findById(id).orElse(null);
    }
}
