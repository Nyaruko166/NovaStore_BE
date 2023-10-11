package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.SizeRepository;
import com.sd64.novastore.request.SizeRequest;
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
    public Page<Size> getPage(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sizeRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Size add(SizeRequest sizeRequest) {
        Size size = sizeRequest.map(new Size());
        return sizeRepository.save(size);
    }

    @Override
    public Size update(SizeRequest sizeRequest, Integer id) {
        Optional<Size> optional = sizeRepository.findById(id);
        return optional.map(o -> {
            o.setId(id);
            o.setName(sizeRequest.getName());
            o.setCreateDate(sizeRequest.getCreateDate());
            o.setUpdateDate(new Date());
            o.setStatus(sizeRequest.getStatus());
            return sizeRepository.save(o);
        }).orElse(null);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<Size> optional = sizeRepository.findById(id);
        return optional.map(o -> {
            o.setStatus(0);
            sizeRepository.save(o);
            return true;
        }).orElse(false);
    }

    @Override
    public Page<Size> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sizeRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
