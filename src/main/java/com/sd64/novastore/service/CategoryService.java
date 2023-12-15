package com.sd64.novastore.service;

import com.sd64.novastore.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Page<Category> getPage(Integer page);

    Boolean add(String name);

    Boolean update(Category category, Integer id);

    Category delete(Integer id);

    Page<Category> search(String name, int page);

    Category detail(Integer id);

    Page<Category> getAllDeleted(int page);

    Category restore(Integer id);

    Page<Category> searchDelete(String name, int page);

    String importExcel(MultipartFile file) throws IOException;
}
