/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.repository;

import com.sd64.novastore.model.OfflineCart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
@Setter
public class OfflineCartRepository {

    private List<OfflineCart> cartSP = new ArrayList<>();

}
