package com.sd64.novastore.service;

import com.sd64.novastore.model.Material;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaterialService {
    List<Material> getAll();

    List<Material> getAllDefault();

    Page<Material> getPage(Integer page);

    Material add(Material material);

    Material update(Material material, Integer id);

    Material delete(Integer id);

    Page<Material> search(String name, int page);

    Material detail(Integer id);
}
