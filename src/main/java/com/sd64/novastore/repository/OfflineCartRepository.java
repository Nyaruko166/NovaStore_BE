/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.repository;

import com.sd64.novastore.model.OfflineCart;
import com.sd64.novastore.model.TempBill;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Getter
@Setter
public class OfflineCartRepository {

    private List<OfflineCart> cartSP = new ArrayList<>();

    private List<TempBill> lstTempBill = new ArrayList<>();

    public OfflineCartRepository() {
        lstTempBill.add(TempBill.builder().billId(0).lstDetailProduct(new ArrayList<>()).build());
    }
}
