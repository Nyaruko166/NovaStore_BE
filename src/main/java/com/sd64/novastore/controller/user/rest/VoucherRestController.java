package com.sd64.novastore.controller.user.rest;

import com.sd64.novastore.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voucher")
public class VoucherRestController {
    @Autowired
    private VoucherRepository voucherRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable Integer id){
        return ResponseEntity.ok(voucherRepository.findById(id));
    }
}
