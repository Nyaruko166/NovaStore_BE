package com.sd64.novastore.service;

import com.sd64.novastore.request.ColorRequest;
import com.sd64.novastore.model.Color;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ColorService {
    List<Color> getAll();

    Page<Color> getAll(Integer page);

    Color add(ColorRequest colorRequest);

    Color update(ColorRequest colorRequest, Integer id);

    Boolean delete(Integer id);
}
