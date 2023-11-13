package com.sd64.novastore.service;

import com.sd64.novastore.model.Customer;

public interface CustomerService {
    Customer findByEmail(String email);
}
