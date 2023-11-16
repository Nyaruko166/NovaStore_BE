package com.sd64.novastore.service;

import com.sd64.novastore.model.Address;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {
    List<Address> getAll();

    Page<Address> getAllPT(Integer page);

    Address add(Address address);

    Address update(Address address, Integer id);

    Address delete(Integer id);

    Address getOne(Integer id);

    Address findAccountDefaultAddress(Integer AccountId);

    List<Address> findAccountAddress(Integer AccountId);

}
