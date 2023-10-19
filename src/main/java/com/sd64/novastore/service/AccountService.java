package com.sd64.novastore.service;

import com.sd64.novastore.request.AccountRequest;
import com.sd64.novastore.model.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    List<Account> getAll();

    Page<Account> getAllPT(Integer page);

    Account add(Account account);

    Account update(Account account, Integer id);

    Account delete(Integer id);

    Page<Account> search(String name, int page);

    Account findOne(Integer id);
}
