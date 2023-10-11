package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.request.ColorRequest;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.repository.ColorRepository;
import com.sd64.novastore.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> getAll(){
        return  colorRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Color> getAll(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return colorRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Color add(ColorRequest colorRequest) {
        Color color = colorRequest.map(new Color());
        return colorRepository.save(color);
    }

    @Override
    public Color update(ColorRequest colorRequest, Integer id) {
        Optional<Color> optional = colorRepository.findById(id);
        Color color = colorRequest.map(optional.get());
        return colorRepository.save(color);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<Color> optional = colorRepository.findById(id);
        if (optional.isPresent()){
            Color color = optional.get();
            color.setStatus(0);
            colorRepository.save(color);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Color> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return colorRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
