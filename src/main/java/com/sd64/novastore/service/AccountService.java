package com.sd64.novastore.service;

import com.sd64.novastore.request.AccountRequest;
import com.sd64.novastore.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {
    List<Account> getAll();

    Page<Account> getAllPT(Integer page);

    Account add(Account account, MultipartFile avt, Integer roleId);

    Account update(Account account, MultipartFile avt, Integer roleId, Integer id);

    Account delete(Integer id);

    Page<Account> search(String name, int page);

    Account findOne(Integer id);

    Account findFirstByEmail(String email);

    byte[] get(Integer accountId);
    
    Integer registerUser(Account user);
}
