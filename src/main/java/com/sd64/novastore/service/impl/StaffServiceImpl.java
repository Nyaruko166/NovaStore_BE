package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Staff;
import com.sd64.novastore.repository.StaffRepository;
import com.sd64.novastore.request.StaffRequest;
import com.sd64.novastore.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public List<Staff> getAll() {
        return staffRepository.findAll();
    }

    @Override
    public Page<Staff> getPage(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return staffRepository.findAll(pageable);
    }

    @Override
    public Staff add(StaffRequest staffRequest) {
        return null;
    }

    @Override
    public Staff update(StaffRequest staffRequest, Integer id) {
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }
}
