package com.sd64.novastore.service;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.request.CategoryRequest;
import com.sd64.novastore.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Page<Category> getPage(Integer page);

    Category add(Category category);

    Category update(Category category, Integer id);

    Category delete(Integer id);

    Page<Category> search(String name, int page);

    Category detail(Integer id);
}
