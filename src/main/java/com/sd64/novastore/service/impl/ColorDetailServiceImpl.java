package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.ColorDetailRequest;
import com.sd64.novastore.model.ColorDetail;
import com.sd64.novastore.repository.ColorDetailRepository;
import com.sd64.novastore.service.ColorDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorDetailServiceImpl implements ColorDetailService {
    @Autowired
    private ColorDetailRepository colorDetailRepository;

    @Override
    public List<ColorDetail> getAll(){
        return  colorDetailRepository.findAll();
    }

    @Override
    public Page<ColorDetail> getAll(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return colorDetailRepository.findAll(pageable);
    }

    @Override
    public ColorDetail add(ColorDetailRequest colorDetailRequest) {
        ColorDetail colorDetail = colorDetailRequest.map(new ColorDetail());
        return colorDetailRepository.save(colorDetail);
    }

    @Override
    public ColorDetail update(ColorDetailRequest colorDetailRequest, Integer id) {
        Optional<ColorDetail> optional = colorDetailRepository.findById(id);
        ColorDetail colorDetail = colorDetailRequest.map(optional.get());
        return colorDetailRepository.save(colorDetail);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<ColorDetail> optional = colorDetailRepository.findById(id);
        if (optional.isPresent()){
            ColorDetail colorDetail = optional.get();
            colorDetailRepository.delete(colorDetail);
            return true;
        } else {
            return false;
        }
    }
}
