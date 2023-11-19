package com.sd64.novastore.service.impl;

import com.sd64.novastore.config.MailConfig;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.repository.AccountRepository;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.service.AuthService;
import com.sd64.novastore.utils.MailUtil;
import com.sd64.novastore.utils.TempCodeManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    TempCodeManager tempCodeManager = new TempCodeManager();

    MailConfig mailConfig = new MailConfig();

    @Override
    public Boolean codeGen(String email, HttpServletRequest request) {
        Account account = accountRepository.findFirstByEmail(email);
        if (account != null) {
            String code = tempCodeManager.createTemporaryCode(request.getRemoteAddr());
            String body = mailUtil.resetPassMailTemplate(email, code, mailConfig.company, mailConfig.contact);
            mailUtil.sendEmail(email, mailConfig.resetPassMail, body);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer codeVerifyAndNewPass(String email, String newPass, String code, HttpServletRequest request) {
        Integer status = tempCodeManager.verifyTemporaryCode(request.getRemoteAddr(), code);
        if (status == 0) {
            tempCodeManager.removeCode(request.getRemoteAddr());
            Account account = accountRepository.findFirstByEmail(email);
            account.setPassword(passwordEncoder.encode(newPass));
            accountRepository.save(account);
            return status;
        }
        return status;
    }

    @Override
    public Boolean codeGenChangePass(String email, HttpServletRequest request) {
        Account account = accountRepository.findFirstByEmail(email);
        if (account != null) {
            String code = tempCodeManager.createTemporaryCode(request.getRemoteAddr());
            String body = mailUtil.changePassMailTemplate(email, code, mailConfig.company, mailConfig.contact);
            mailUtil.sendEmail(email, mailConfig.changePassMail, body);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer codeVerifyAndChangePass(String email, String currentPass, String reNewPass, String newPass, String code, HttpServletRequest request) {
        Account account = accountRepository.findFirstByEmail(email);
        Integer status = tempCodeManager.verifyTemporaryCode(request.getRemoteAddr(), code);
        if (account == null) {
            return 1;
        } else if (!newPass.equals(reNewPass)) {
            return 2;
        } else if (!passwordEncoder.matches(currentPass, account.getPassword())) {
            return 3;
        } else if (status == 1) {
            return 4;
        } else if (status == 2) {
            return 5;
        } else {
            tempCodeManager.removeCode(request.getRemoteAddr());
            account.setPassword(passwordEncoder.encode(newPass));
            accountRepository.save(account);
            return 0;
        }
    }
}
