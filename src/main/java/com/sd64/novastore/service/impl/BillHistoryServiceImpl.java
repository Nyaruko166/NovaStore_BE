package com.sd64.novastore.service.impl;

import com.sd64.novastore.request.BillHistoryRequest;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillHistory;
import com.sd64.novastore.repository.BillHistoryRepository;
import com.sd64.novastore.service.BillHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillHistoryServiceImpl implements BillHistoryService {
    @Autowired
    private BillHistoryRepository billHistoryRepository;
    @Override
    public List<BillHistory> getAll() {
        return billHistoryRepository.findAllByStatus(1);
    }

    @Override
    public Page<BillHistory> getAllPT(Integer page) {
        Pageable pageable= PageRequest.of(page,5);
        return billHistoryRepository.findAllByStatus(pageable,1);
    }

    @Override
    public BillHistory add(BillHistoryRequest billHistoryRequest) {
        BillHistory billHistory= billHistoryRequest.map(new BillHistory());
        return billHistoryRepository.save(billHistory);
    }

    @Override
    public BillHistory update(BillHistoryRequest billHistoryRequest, Integer id) {
        Optional<BillHistory> billHistoryOptional= billHistoryRepository.findById(id);
        return billHistoryOptional.map(billHistory -> {
            billHistory.setName(billHistoryRequest.getName());
            billHistory.setDescribe(billHistoryRequest.getDescribe());
            billHistory.setCreateDate(Date.valueOf(billHistoryRequest.getCreateDate()));
            billHistory.setUpdateDate(Date.valueOf(billHistoryRequest.getUpdateDate()));
            billHistory.setStatus(Integer.valueOf(billHistoryRequest.getStatus()));
            billHistory.setBill(Bill.builder().id(Integer.valueOf(billHistoryRequest.getBillId())).build());
            billHistory.setAccount(Account.builder().id(Integer.valueOf(billHistoryRequest.getAccountID())).build());
            return billHistoryRepository.save(billHistory);
        }).orElse(null);
    }

    @Override
    public BillHistory delete(Integer id) {
        Optional<BillHistory> billHistoryOptional= billHistoryRepository.findById(id);
        return billHistoryOptional.map(billHistory -> {
            billHistory.setStatus(0);
            billHistoryRepository.save(billHistory);
            return billHistory;
        }).orElse(null);
    }
}
