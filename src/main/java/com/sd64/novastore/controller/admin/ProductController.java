package com.sd64.novastore.controller.admin;

import com.sd64.novastore.request.ProductRequest;
import com.sd64.novastore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(productService.getAll(page).getContent());
    }

//    @GetMapping("/page")
//    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "page") Integer page) {
//        return ResponseEntity.ok(productService.getAll(page).getContent());
//    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid ProductRequest productRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(productService.add(productRequest));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid ProductRequest productRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(productService.update(productRequest, id));
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (productService.delete(id)) {
            return ResponseEntity.ok("Xoá thành công");
        } else {
            return ResponseEntity.ok("Xoá thất bại");
        }
    }
}
