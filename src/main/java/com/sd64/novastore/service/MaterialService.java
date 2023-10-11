package com.sd64.novastore.service;

import com.sd64.novastore.request.MaterialRequest;
import com.sd64.novastore.model.Material;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaterialService {
    List<Material> getAll();

    Page<Material> getAll(Integer page);

    Material add(MaterialRequest materialRequest);

    Material update(MaterialRequest materialRequest, Integer id);

    Boolean delete(Integer id);

    Page<Material> search(String name, int page);
}
