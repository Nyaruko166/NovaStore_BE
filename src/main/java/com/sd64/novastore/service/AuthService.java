package com.sd64.novastore.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    Boolean codeGen(String email, HttpServletRequest request);

    Boolean codeVerifyAndNewPass(String email, String newPass, String code, HttpServletRequest request);
}
