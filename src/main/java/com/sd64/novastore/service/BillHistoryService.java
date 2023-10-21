package com.sd64.novastore.service;

import com.sd64.novastore.model.BillHistory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillHistoryService {
    List<BillHistory> getAll();

    Page<BillHistory> getAll(Integer page);

    BillHistory add(BillHistory billHistory);

    BillHistory update(BillHistory billHistory, Integer id);

    BillHistory delete(Integer id);

    BillHistory getOne(Integer id);
}
