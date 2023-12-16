/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.controller.admin;

import com.sd64.novastore.response.CustomerResponse;
import com.sd64.novastore.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/nova/account")
public class RestAccountController {

    @Autowired
    private AccountService accountService;

}
