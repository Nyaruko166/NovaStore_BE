package com.sd64.novastore.service;

import com.sd64.novastore.dto.ColorDetailRequest;
import com.sd64.novastore.model.ColorDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ColorDetailService {
    List<ColorDetail> getAll();

    Page<ColorDetail> getAll(Integer page);

    ColorDetail add(ColorDetailRequest colorDetailRequest);

    ColorDetail update(ColorDetailRequest colorDetailRequest, Integer id);

    Boolean delete(Integer id);
}
