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
        return sizeRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Size> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sizeRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    @Override
    public Size add(String name) {
        Size size = new Size();
        size.setName(name);
        size.setStatus(1);
        size.setCreateDate(new Date());
        size.setUpdateDate(new Date());
        return sizeRepository.save(size);
    }

    @Override
    public Size update(Integer id, String name) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (optional.isPresent()) {
            Size updateSize = optional.get();
            updateSize.setId(id);
            updateSize.setName(name);
            updateSize.setStatus(1);
            updateSize.setCreateDate(optional.get().getCreateDate());
            updateSize.setUpdateDate(new Date());
            return sizeRepository.save(updateSize);
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

    @Override
    public Size detail(Integer id) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }
}
