package com.sd64.novastore.controller.admin;

import com.sd64.novastore.request.MaterialRequest;
import com.sd64.novastore.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(materialService.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "page") Integer page) {
        return ResponseEntity.ok(materialService.getAll(page).getContent());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid MaterialRequest materialRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(materialService.add(materialRequest));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid MaterialRequest materialRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(materialService.update(materialRequest, id));
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (materialService.delete(id)) {
            return ResponseEntity.ok("Xoá thành công");
        } else {
            return ResponseEntity.ok("Xoá thất bại");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String name, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(materialService.search(name, page).getContent());
    }
}
