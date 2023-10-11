package com.sd64.novastore.service;

import com.sd64.novastore.request.AddressRequest;
import com.sd64.novastore.model.Address;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {
    List<Address> getAll();

    Page<Address> getAllPT(Integer page);

    Address add(AddressRequest addressRequest);

    Address update(AddressRequest addressRequest, Integer id);

    Address delete(Integer id);

}
