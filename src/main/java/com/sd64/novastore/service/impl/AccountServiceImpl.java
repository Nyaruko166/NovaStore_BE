package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.request.AccountRequest;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.repository.AccountRepository;
import com.sd64.novastore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import java.sql.Date;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Account> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return accountRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Account add(Account account) {
        account.setStatus(1);
        account.setCreateDate(new java.util.Date());
        account.setUpdateDate(new java.util.Date());
        return accountRepository.save(account);
    }

    @Override
    public Account update(Account account, Integer id) {
        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isPresent()) {
            Account updateBrand = optional.get();
            account.setId(id);
            account.setStatus(updateBrand.getStatus());
            account.setCreateDate(updateBrand.getCreateDate());
            account.setUpdateDate(new Date());
            return accountRepository.save(account);
        } else {
            return null;
        }

    }

    @Override
    public Account delete(Integer id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.map(account -> {
            account.setStatus(0);
            accountRepository.save(account);
            return account;
        }).orElse(null);
    }

    @Override
    public Page<Account> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return accountRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
