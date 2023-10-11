package com.sd64.novastore.service;

import com.sd64.novastore.dto.BillRequest;
import com.sd64.novastore.model.Bill;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillService {
    List<Bill> getAll();

    Page<Bill> getAllPT(Integer page);

    Bill add(BillRequest billRequest);

    Bill update(BillRequest billRequest, Integer id);

    Bill delete(Integer id);
}
