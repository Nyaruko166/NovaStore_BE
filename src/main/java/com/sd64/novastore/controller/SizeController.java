package com.sd64.novastore.controller;

import com.sd64.novastore.request.PromotionRequest;
import com.sd64.novastore.request.SizeRequest;
import com.sd64.novastore.service.PromotionService;
import com.sd64.novastore.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(sizeService.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(sizeService.getPage(page).getContent());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid SizeRequest sizeRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.ok(error);
        } else {
            return ResponseEntity.ok(sizeService.add(sizeRequest));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody SizeRequest sizeRequest, @PathVariable Integer id) {
        return ResponseEntity.ok(sizeService.update(sizeRequest, id));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (sizeService.delete(id)) {
            return ResponseEntity.ok("Delete Success");
        } else {
            return ResponseEntity.ok("Delete Fail");
        }
    }
}
