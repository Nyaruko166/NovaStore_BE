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
    public Boolean codeVerifyAndNewPass(String email, String newPass, String code, HttpServletRequest request) {
        if (tempCodeManager.verifyTemporaryCode(request.getRemoteAddr(), code)) {
            Account account = accountRepository.findFirstByEmail(email);
            account.setPassword(passwordEncoder.encode(newPass));
            accountRepository.save(account);
            return true;
        }
        return false;
    }


}
