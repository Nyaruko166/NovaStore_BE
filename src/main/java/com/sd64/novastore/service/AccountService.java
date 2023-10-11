package com.sd64.novastore.service;

import com.sd64.novastore.request.AccountRequest;
import com.sd64.novastore.model.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    List<Account> getAll();

    Page<Account> getAllPT(Integer page);

    Account add(AccountRequest accountRequest);

    Account update(AccountRequest accountRequest, Integer id);

    Account delete(Integer id);
}
