package com.sd64.novastore.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    Boolean codeGen(String email, HttpServletRequest request);

    Boolean codeGenChangePass(String email, HttpServletRequest request);

    Integer codeVerifyAndNewPass(String email, String newPass, String code, HttpServletRequest request);

    Integer codeVerifyAndChangePass(String email, String currentPass, String reNewPass, String newPass, String code, HttpServletRequest request);
}
