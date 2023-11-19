package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.repository.ColorRepository;
import com.sd64.novastore.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> getAll(){
        return colorRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Color> getPage(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return colorRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    @Override
    public Color add(String name) {
        Color color = new Color();
        color.setName(name);
        color.setStatus(1);
        color.setCreateDate(new java.util.Date());
        color.setUpdateDate(new java.util.Date());
        return colorRepository.save(color);
    }

    @Override
    public Color update(Integer id, String name) {
        Optional<Color> optional = colorRepository.findById(id);
        if (optional.isPresent()) {
            Color updateColor = optional.get();
            updateColor.setId(id);
            updateColor.setName(name);
            updateColor.setStatus(updateColor.getStatus());
            updateColor.setCreateDate(updateColor.getCreateDate());
            updateColor.setUpdateDate(new Date());
            return colorRepository.save(updateColor);
        } else {
            return null;
        }
    }

    @Override
    public Color delete(Integer id) {
        Optional<Color> optional = colorRepository.findById(id);
        if (optional.isPresent()) {
            Color color = optional.get();
            color.setStatus(0);
            return colorRepository.save(color);
        } else {
            return null;
        }
    }

    @Override
    public Page<Color> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return colorRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }

    @Override
    public Color detail(Integer id) {
        Optional<Color> optional = colorRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }
}
