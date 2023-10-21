package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.BillHistory;
import com.sd64.novastore.repository.BillHistoryRepository;
import com.sd64.novastore.service.BillHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Page<BillHistory> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return billHistoryRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    @Override
    public BillHistory add(BillHistory billHistory) {
        billHistory.setCreateDate(new Date());
        billHistory.setUpdateDate(new Date());
        billHistory.setStatus(1);
        return billHistoryRepository.save(billHistory);
    }

    @Override
    public BillHistory update(BillHistory billHistory, Integer id) {
        Optional<BillHistory> optional = billHistoryRepository.findById(id);
        if (optional.isPresent()){
            BillHistory oldBillHistory = optional.get();
            billHistory.setId(oldBillHistory.getId());
            billHistory.setCreateDate(oldBillHistory.getCreateDate());
            billHistory.setUpdateDate(new Date());
            billHistory.setStatus(oldBillHistory.getStatus());
            return billHistoryRepository.save(billHistory);
        } else {
            return null;
        }
    }

    @Override
    public BillHistory delete(Integer id) {
        Optional<BillHistory> optional = billHistoryRepository.findById(id);
        if (optional.isPresent()){
            BillHistory billHistory = optional.get();
            billHistory.setStatus(0);
            return billHistoryRepository.save(billHistory);
        } else {
            return null;
        }
    }

    @Override
    public BillHistory getOne(Integer id) {
        return billHistoryRepository.findById(id).orElse(null);
    }
}
