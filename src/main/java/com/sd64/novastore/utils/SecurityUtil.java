package com.sd64.novastore.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String currentRole = authentication.getAuthorities().toString();
//            String credential = authentication.getCredentials().toString();
            String details = authentication.getDetails().toString();
            return currentUserName + currentRole + details;
        }
        return null;
    }

}
