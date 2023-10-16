package com.sd64.novastore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping("/index")
    public String test(){
        return "/admin/crud";
    }
}
