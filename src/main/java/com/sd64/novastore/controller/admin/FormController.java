package com.sd64.novastore.controller.admin;

import com.sd64.novastore.request.FormRequest;
import com.sd64.novastore.service.FormService;
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
@RequestMapping("/form")
public class FormController {
    @Autowired
    private FormService formService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(formService.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "page") Integer page) {
        return ResponseEntity.ok(formService.getAll(page).getContent());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid FormRequest formRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(formService.add(formRequest));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid FormRequest formRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok(formService.update(formRequest, id));
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (formService.delete(id)) {
            return ResponseEntity.ok("Delete Success");
        } else {
            return ResponseEntity.ok("Delete Fail");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String name, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(formService.search(name, page).getContent());
    }
}
