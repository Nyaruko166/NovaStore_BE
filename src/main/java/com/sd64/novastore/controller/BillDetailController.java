package com.sd64.novastore.controller;

import com.sd64.novastore.dto.BillDetailRequest;
import com.sd64.novastore.service.BillDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/bill_detail")
public class BillDetailController {
    @Autowired
    private BillDetailService billDetailService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(billDetailService.getAll());
    }

    @GetMapping("/getallpt")
    public ResponseEntity<?> getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(billDetailService.getAllPT(page).getContent());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(billDetailService.delete(id));
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid BillDetailRequest billDetailRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(billDetailService.add(billDetailRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid BillDetailRequest billDetailRequest, @PathVariable Integer id, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(billDetailService.update(billDetailRequest, id));

    }
}
