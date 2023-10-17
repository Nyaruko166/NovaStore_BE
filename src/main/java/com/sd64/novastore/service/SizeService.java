package com.sd64.novastore.service;

import com.sd64.novastore.model.Size;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SizeService {

    List<Size> getAll();

    Page<Size> getPage(Integer page);

    Size add(Size size);

    Size update(Size size, Integer id);

    Size delete(Integer id);

    Page<Size> search(String name, int page);
}
