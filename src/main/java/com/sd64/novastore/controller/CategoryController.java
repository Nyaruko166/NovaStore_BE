package com.sd64.novastore.controller;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.model.Category;
import com.sd64.novastore.request.CategoryRequest;
import com.sd64.novastore.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Category> listCategory = categoryService.getAll();
        model.addAttribute("listCategory", listCategory);
        return "";
    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Category> pageCategory = categoryService.getPage(page);
        model.addAttribute("pageCategory", pageCategory.getContent());
        return "";
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }


    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Category") Category category) {
        categoryService.add(category);
        return "redirect:/admin/category/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute("Category") Category category) {
        categoryService.update(category, id);
        return "redirect:/admin/category/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String nameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Category> listCategory = categoryService.search(nameSearch, page);
        model.addAttribute("listBrand", listCategory.getContent());
        return "";
    }
}
