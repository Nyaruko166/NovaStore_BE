package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.AddressRequest;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Address;
import com.sd64.novastore.repository.AddressRepository;
import com.sd64.novastore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AddressImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public List<Address> getAll() {
        return addressRepository.findAllByStatus(1);
    }

    @Override
    public Page<Address> getAllPT(Integer page) {
        Pageable pageable= PageRequest.of(page,5);
        return addressRepository.findAllByStatus(pageable,1);
    }

    @Override
    public Address add(AddressRequest addressRequest) {
        Address address= addressRequest.map(new Address());
        return addressRepository.save(address);
    }

    @Override
    public Address update(AddressRequest addressRequest, Integer id) {
        Optional<Address> addressOptional= addressRepository.findById(id);
        return addressOptional.map(address -> {
            address.setCity(addressRequest.getCity());
            address.setDistrict(addressRequest.getDistrict());
            address.setWard(addressRequest.getWard());
            address.setSpecificAddress(addressRequest.getSpecificAddress());
            address.setCreateDate(java.util.Date.from(Instant.parse(addressRequest.getCreateDate())));
            address.setUpdateDate(Date.from(Instant.parse(addressRequest.getUpdateDate())));
            address.setStatus(Integer.valueOf(addressRequest.getStatus()));
            address.setAccount(Account.builder().id(Integer.valueOf(addressRequest.getAccountID())).build());
            return addressRepository.save(address);
        }).orElse(null);
    }

    @Override
    public Address delete(Integer id) {
        Optional<Address> addressOptional= addressRepository.findById(id);
        return addressOptional.map(address -> {
            address.setStatus(0);
            addressRepository.save(address);
            return address;
        }).orElse(null);
    }
}
