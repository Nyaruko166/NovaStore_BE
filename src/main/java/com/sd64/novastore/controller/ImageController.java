package com.sd64.novastore.controller;

import com.sd64.novastore.request.ImageRequest;
import com.sd64.novastore.service.ImageService;
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
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(imageService.getAll());
    }

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "page") Integer page) {
        return ResponseEntity.ok(imageService.getAll(page).getContent());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid ImageRequest imageRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(imageService.add(imageRequest));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid ImageRequest imageRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(imageService.update(imageRequest, id));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (imageService.delete(id)) {
            return ResponseEntity.ok("Xoá thành công");
        } else {
            return ResponseEntity.ok("Xoá thất bại");
        }
    }
}
