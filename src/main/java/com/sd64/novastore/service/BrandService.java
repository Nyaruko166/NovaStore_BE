package com.sd64.novastore.service;

import com.sd64.novastore.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BrandService {
    List<Brand> getAll();

    Page<Brand> getPage(Integer page);

    Boolean add(String name);

    Boolean update(Integer id, String name);

    Brand delete(Integer id);

    Page<Brand> search(String name, int page);

    Brand detail(Integer id);

    Page<Brand> getAllDeleted(int page);

    Brand restore(Integer id);

    Page<Brand> searchDelete(String name, int page);

    String importExcel(MultipartFile file) throws IOException;
}
