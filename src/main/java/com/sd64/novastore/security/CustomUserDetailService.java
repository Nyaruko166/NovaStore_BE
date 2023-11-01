package com.sd64.novastore.security;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Role;
import com.sd64.novastore.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findFirstByEmail(email);
        if (account != null) {
            return new CustomUser(account);
        }
        throw new UsernameNotFoundException("User not found");
    }
}
