package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.model.Category;
import com.sd64.novastore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

//    @GetMapping("/all")
//    public String getAll(Model model) {
//        List<Category> listCategory = categoryService.getAll();
//        model.addAttribute("listCategory", listCategory);
//        return "";
//    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Category> pageCategory = categoryService.getPage(page);
        model.addAttribute("pageCategory", pageCategory);
        model.addAttribute("page", page);
        return "admin/category/category";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Category category = categoryService.detail(id);
        model.addAttribute("category", category);
        return "/admin/category/category-detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        categoryService.delete(id);
        redirectAttributes.addFlashAttribute("delete", "Xóa thành công");
        return "redirect:/admin/category/page";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        categoryService.add(category);
        redirectAttributes.addFlashAttribute("add", "Thêm thành công");
        return "redirect:/admin/category/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        categoryService.update(category, id);
        redirectAttributes.addFlashAttribute("update", "Sửa thành công");
        return "redirect:/admin/category/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String categoryNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Category> pageCategory = categoryService.search(categoryNameSearch, page);
        if ("".equals(categoryNameSearch) || categoryNameSearch.isEmpty()) {
            return "redirect:/admin/category/page";
        }
        model.addAttribute("categoryNameSearch", categoryNameSearch);
        model.addAttribute("pageCategory", pageCategory);
        return "admin/category/category";
    }
}
