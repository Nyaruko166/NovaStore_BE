package com.sd64.novastore.service;

import com.sd64.novastore.model.Color;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ColorService {
    List<Color> getAll();

    Page<Color> getPage(Integer page);

    Color add(String name);

    Color update(Integer id, String name);

    Color delete(Integer id);

    Page<Color> search(String name, int page);

    Color detail(Integer id);
}
