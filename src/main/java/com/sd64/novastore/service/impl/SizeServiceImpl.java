package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
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

    private Boolean checkName(String name) {
        Optional<Size> optionalSize = sizeRepository.findByName(name);
        if (optionalSize.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean add(String name) {
        if (checkName(name)) {
            Size size = new Size();
            size.setName(name);
            size.setStatus(1);
            size.setCreateDate(new Date());
            size.setUpdateDate(new Date());
            sizeRepository.save(size);
            size.setCode("S"+size.getId());
            sizeRepository.save(size);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean update(Integer id, String name) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (optional.isPresent() && checkName(name)) {
            Size updateSize = optional.get();
            updateSize.setId(id);
            updateSize.setName(name);
            updateSize.setUpdateDate(new Date());
            sizeRepository.save(updateSize);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Size delete(Integer id) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (optional.isPresent()) {
            Size size = optional.get();
            size.setStatus(0);
            size.setUpdateDate(new Date());
            return sizeRepository.save(size);
        } else {
            return null;
        }
    }

    @Override
    public Page<Size> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sizeRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 1, pageable);
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

    @Override
    public Page<Size> getAllSizeDelete(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sizeRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 0);
    }

    @Override
    public Size restore(Integer id) {
        Optional<Size> optionalSize = sizeRepository.findById(id);
        if (optionalSize.isPresent()) {
            Size restoreSize = optionalSize.get();
            restoreSize.setStatus(1);
            restoreSize.setUpdateDate(new Date());
            return sizeRepository.save(restoreSize);
        } else {
            return null;
        }
    }

    @Override
    public Page<Size> searchDeleted(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sizeRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 0, pageable);
    }
}
