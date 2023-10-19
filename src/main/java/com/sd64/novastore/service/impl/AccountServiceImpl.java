package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.repository.AccountRepository;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    public Account findOne(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account add(Account account, MultipartFile avt) {
        String uploadDir = "./src/main/resources/static/assets/avatars/";
        String fileName = StringUtils.cleanPath(avt.getOriginalFilename());
        account.setStatus(1);
        account.setCreateDate(new java.util.Date());
        account.setUpdateDate(new java.util.Date());
        Account accountLasted = accountRepository.save(account);
        String uid = "uid_" + accountLasted.getId();
        String avtPath = FileUtil.copyFile(avt, fileName, uploadDir);
        String anhUrl = FileUtil.rename(avtPath, uid);
        accountLasted.setAvatar(anhUrl);
//        System.out.println(anhUrl);
//        System.out.println(avtPath);
//        System.out.println(uid);
        return accountRepository.save(accountLasted);
    }

    @Override
    public Account update(Account account, MultipartFile avt, Integer id) {
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
        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isPresent()) {
            Account account = optional.get();
            account.setStatus(0);
            return accountRepository.save(account);
        } else {
            return null;
        }
    }

    @Override
    public Page<Account> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return accountRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
