package com.sd64.novastore.service;

import com.sd64.novastore.model.Form;
import com.sd64.novastore.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MaterialService {
    List<Material> getAll();

    Page<Material> getPage(Integer page);

    Boolean add(String name);

    Boolean update(Material material, Integer id);

    Material delete(Integer id);

    Page<Material> search(String name, int page);

    Material detail(Integer id);

    Page<Material> getAllDeleted(int page);

    Material restore(Integer id);

    Page<Material> searchDelete(String name, int page);

    Material getOne(Integer id);

    String importExcel(MultipartFile file) throws IOException;
}
