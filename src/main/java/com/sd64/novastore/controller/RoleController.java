package com.sd64.novastore.controller;

import com.sd64.novastore.request.PromotionRequest;
import com.sd64.novastore.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(promotionService.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(promotionService.getPage(page).getContent());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid PromotionRequest promotionRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.ok(error);
        } else {
            return ResponseEntity.ok(promotionService.add(promotionRequest));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody PromotionRequest promotionRequest, @PathVariable Integer id) {
        return ResponseEntity.ok(promotionService.update(promotionRequest, id));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (promotionService.delete(id)) {
            return ResponseEntity.ok("Delete Success");
        } else {
            return ResponseEntity.ok("Delete Fail");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String name, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(promotionService.search(name, page).getContent());
    }
}
