package com.sd64.novastore.controller;

import com.sd64.novastore.dto.ColorRequest;
import com.sd64.novastore.service.ColorService;
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
@RequestMapping("/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(colorService.getAll());
    }

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "page") Integer page) {
        return ResponseEntity.ok(colorService.getAll(page).getContent());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid ColorRequest colorRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(colorService.add(colorRequest));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid ColorRequest colorRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(colorService.update(colorRequest, id));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (colorService.delete(id)) {
            return ResponseEntity.ok("Xoá thành công");
        } else {
            return ResponseEntity.ok("Xoá thất bại");
        }
    }
}
