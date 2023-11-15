package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Customer;
import com.sd64.novastore.repository.CustomerRepository;
import com.sd64.novastore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
