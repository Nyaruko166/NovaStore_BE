package com.sd64.novastore.service;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Size;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ColorService {
    List<Color> getAll();

    Page<Color> getPage(Integer page);

    Boolean add(String name);

    Boolean update(Integer id, String name);

    Color delete(Integer id);

    Page<Color> search(String name, int page);

    Color detail(Integer id);

    Page<Color> getAllSizeDelete(int page);

    Color restore(Integer id);

    Page<Color> searchDeleted(String name, int page);
}
