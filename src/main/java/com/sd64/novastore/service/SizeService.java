package com.sd64.novastore.service;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Size;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SizeService {

    List<Size> getAll();

    Page<Size> getPage(Integer page);

    Size add(String name);

    Size update(Integer id, String name);

    Size delete(Integer id);

    Page<Size> search(String name, int page);

    Size detail(Integer id);
}