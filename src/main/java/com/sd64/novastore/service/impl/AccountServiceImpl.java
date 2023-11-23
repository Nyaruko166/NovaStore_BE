package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Role;
import com.sd64.novastore.repository.AccountRepository;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final String uploadDir = "./src/main/resources/static/assets/avatars/";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public Account findFirstByEmail(String email) {
        return accountRepository.findFirstByEmail(email);
    }

    @Override
    public Account findOne(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account add(Account account, MultipartFile avt, Integer roleId) {
        String fileName = StringUtils.cleanPath(avt.getOriginalFilename());
        account.setStatus(1);
        account.setRole(Role.builder().id(roleId).build());
        account.setCreateDate(new java.util.Date());
        account.setUpdateDate(new java.util.Date());
        String rawPassword = account.getPassword();
        account.setPassword(passwordEncoder.encode(rawPassword));
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
    public Account update(Account account, MultipartFile avt, Integer roleId, Integer id) {
        String fileName = StringUtils.cleanPath(avt.getOriginalFilename());
        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isPresent()) {
            Account updateAccount = optional.get();
            String oldAvt = updateAccount.getAvatar();
            account.setId(id);
            account.setStatus(updateAccount.getStatus());
            if (account.getPassword().isBlank()) {
                account.setPassword(updateAccount.getPassword());
            } else {
                String rawPassword = account.getPassword();
                account.setPassword(passwordEncoder.encode(rawPassword));
            }
            account.setRole(Role.builder().id(roleId).build());
            account.setCreateDate(updateAccount.getCreateDate());
            account.setUpdateDate(new Date());
            if (!avt.isEmpty()) {
                File fileToDelete = new File(uploadDir + oldAvt);
                fileToDelete.delete();
                String avtPath = FileUtil.copyFile(avt, fileName, uploadDir);
                String anhUrl = FileUtil.rename(avtPath, FileUtil.rmExt(oldAvt));
                account.setAvatar(anhUrl);
            } else {
                account.setAvatar(oldAvt);
            }
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

    private byte[] convert(String imagePath) throws IOException {
        // Create a FileInputStream object to read the image file.
        FileInputStream fis = new FileInputStream(imagePath);

        // Create a ByteArrayOutputStream object to store the image data in bytes.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // Read the image data from the FileInputStream object and write it to the ByteArrayOutputStream object.
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        // Close the FileInputStream and ByteArrayOutputStream objects.
        fis.close();
        bos.close();

        // Get the byte array containing the image data from the ByteArrayOutputStream object.
        byte[] imageData = bos.toByteArray();
        return imageData;
    }

    private String getImagePath(String fileName) {
        String currentProjectPath = System.getProperty("user.dir");
        return currentProjectPath + File.separator + "src/main/resources/static/assets/avatars"
                + File.separator + fileName;
    }
    @Override
    public byte[] get(Integer accountId) {
        var account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            log.info("image id = {} is not exist on DB", accountId);
            return null;
        }
        try {
            return convert(getImagePath(account.getAvatar()));
        } catch (IOException e) {
            log.error("Convert image fail, image id = {}", accountId);
            return null;
        }
    }
}
