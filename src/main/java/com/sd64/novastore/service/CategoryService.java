package com.sd64.novastore.service;

import com.sd64.novastore.request.CategoryRequest;
import com.sd64.novastore.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Page<Category> getAllPT(Integer page);

    Category add(CategoryRequest categoryRequest);

    Category update(CategoryRequest categoryRequest, Integer id);

    Category delete(Integer id);

    Page<Category> search(String name, int page);
}
