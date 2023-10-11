package com.sd64.novastore.controller;

import com.sd64.novastore.request.VoucherRequest;
import com.sd64.novastore.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(voucherService.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(voucherService.getPage(page).getContent());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid VoucherRequest voucherRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.ok(error);
        } else {
            return ResponseEntity.ok(voucherService.add(voucherRequest));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody VoucherRequest voucherRequest, @PathVariable Integer id) {
        return ResponseEntity.ok(voucherService.update(voucherRequest, id));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (voucherService.delete(id)) {
            return ResponseEntity.ok("Delete Success");
        } else {
            return ResponseEntity.ok("Delete Fail");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String name, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(voucherService.search(name, page).getContent());
    }
}
