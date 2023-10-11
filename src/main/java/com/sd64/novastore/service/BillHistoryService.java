package com.sd64.novastore.service;

import com.sd64.novastore.dto.BillHistoryRequest;
import com.sd64.novastore.model.BillHistory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillHistoryService {
    List<BillHistory> getAll();

    Page<BillHistory> getAllPT(Integer page);

    BillHistory add(BillHistoryRequest billHistoryRequest);

    BillHistory update(BillHistoryRequest billHistoryRequest, Integer id);

    BillHistory delete(Integer id);
}
