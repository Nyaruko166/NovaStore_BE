package com.sd64.novastore.controller.admin;

import com.sd64.novastore.request.AccountRequest;
import com.sd64.novastore.service.AccountService;
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
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(accountService.getAllPT(page).getContent());
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(accountService.delete(id));
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid AccountRequest accountRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(accountService.add(accountRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid AccountRequest accountRequest, @PathVariable Integer id, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(accountService.update(accountRequest, id));

    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String name, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(accountService.search(name, page).getContent());
    }
}