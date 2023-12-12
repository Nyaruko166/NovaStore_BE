/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.controller.admin;

import com.sd64.novastore.service.OfflineCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RestOfflineCartController {

    @Autowired
    private OfflineCartService offlineCartService;

    @PostMapping("/nova/pos/add")
    public ResponseEntity addToCart(@RequestBody Map<String, String> response) {

        System.out.println(offlineCartService.addToCart("1", response.get("data"), 1));
        return new ResponseEntity(HttpStatus.OK);
    }

}
