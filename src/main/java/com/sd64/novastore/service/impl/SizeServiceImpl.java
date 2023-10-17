package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.SizeRepository;
import com.sd64.novastore.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<Size> getAll() {
        return sizeRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Size> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sizeRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    @Override
    public Size add(Size size) {
        size.setStatus(1);
        size.setCreateDate(new java.util.Date());
        size.setUpdateDate(new java.util.Date());
        return sizeRepository.save(size);
    }

    @Override
    public Size update(Size size, Integer id) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (optional.isPresent()) {
            Size updateSize = optional.get();
            size.setId(id);
            size.setName(updateSize.getName());
            size.setStatus(updateSize.getStatus());
            size.setCreateDate(updateSize.getCreateDate());
            size.setUpdateDate(new Date());
            return sizeRepository.save(size);
        } else {
            return null;
        }
    }

    @Override
    public Size delete(Integer id) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (optional.isPresent()) {
            Size size = optional.get();
            size.setStatus(0);
            return sizeRepository.save(size);
        } else {
            return null;
        }
    }

    @Override
    public Page<Size> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sizeRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
