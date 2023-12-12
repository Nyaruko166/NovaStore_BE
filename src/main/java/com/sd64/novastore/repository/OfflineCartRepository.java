/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Getter
@Setter
public class OfflineCartRepository {

    private Map<String, Integer> cartSP = new HashMap<>();

}
