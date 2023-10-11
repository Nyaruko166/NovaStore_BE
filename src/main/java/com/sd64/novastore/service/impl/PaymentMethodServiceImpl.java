package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.PaymentMethodRequest;
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
    public List<PaymentMethod> getAll(){
        return  paymentMethodRepository.findAllByStatus(1);
    }

    @Override
    public Page<PaymentMethod> getAll(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return paymentMethodRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public PaymentMethod add(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = paymentMethodRequest.map(new PaymentMethod());
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod update(PaymentMethodRequest paymentMethodRequest, Integer id) {
        Optional<PaymentMethod> optional = paymentMethodRepository.findById(id);
        PaymentMethod paymentMethod = paymentMethodRequest.map(optional.get());
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<PaymentMethod> optional = paymentMethodRepository.findById(id);
        if (optional.isPresent()){
            PaymentMethod paymentMethod = optional.get();
            paymentMethodRepository.delete(paymentMethod);
            return true;
        } else {
            return false;
        }
    }
}
