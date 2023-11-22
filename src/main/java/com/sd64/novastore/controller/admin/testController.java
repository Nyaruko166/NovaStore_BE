package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.repository.AccountRepository;
import com.sd64.novastore.utils.TempCodeManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class testController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository repository;

    TempCodeManager tempCodeManager = new TempCodeManager();

    @GetMapping("/index")
    public String test() {
        return "/admin/crud";
    }

    @GetMapping("/p")
    public String testP(HttpServletRequest request) {
        Account test = repository.findFirstByEmail("51241");
        if (passwordEncoder.matches("123123", test.getPassword())) {
            System.out.println("Ngonnnnnnnnnnnnnn");
//            System.out.println(request.getRemoteAddr());
            String id = request.getRemoteAddr();
            String generatedCode = tempCodeManager.createTemporaryCode(id);
            System.out.println("Generated code: " + generatedCode + " Id: " + id);

        } else {
            System.out.println("deongon");
        }
        return "forgor";
//        private void scheduleCodeRemoval(String id) {
//            new Thread(() -> {
//                try {
//                    Thread.sleep(EXPIRATION_TIME);
//                    temporaryCodes.remove(id);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
    }

//    @GetMapping("/t/{code}")
//    public String testCode(@PathVariable String code, HttpServletRequest request) {
//        if (tempCodeManager.verifyTemporaryCode(request.getRemoteAddr(), code)) {
//            System.out.println("Bú");
//        } else {
//            System.out.println("Không bú");
//        }
//        return "register";
//    }

}
