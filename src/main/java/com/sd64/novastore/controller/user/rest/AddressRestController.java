package com.sd64.novastore.controller.user.rest;

import com.sd64.novastore.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressRestController {
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable Integer id){
        return ResponseEntity.ok(addressRepository.findById(id));
    }
}
