package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/nova/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

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
        redirectAttributes.addFlashAttribute("mess", "Xóa dữ liệu thành công");
        return "redirect:/nova/category/page";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute Category category,
                      RedirectAttributes redirectAttributes) {
        if (categoryService.add(category)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/category/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên loại đã tồn tại");
            return "redirect:/nova/category/page";
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute Category category,
                         RedirectAttributes redirectAttributes) {
        if (categoryService.update(category, id)) {
            redirectAttributes.addFlashAttribute("mess", "Sửa dữ liệu thành công");
            return "redirect:/nova/category/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên loại đã tồn tại");
            return "redirect:/nova/category/detail/" + id;
        }
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String categoryNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Category> pageCategory = categoryService.search(categoryNameSearch, page);
        if ("".equals(categoryNameSearch) || categoryNameSearch.isEmpty()) {
            return "redirect:/nova/category/page";
        }
        model.addAttribute("categoryNameSearch", categoryNameSearch);
        model.addAttribute("pageCategory", pageCategory);
        return "admin/category/category";
    }

    @GetMapping("/view-restore")
    public String viewRestore(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Category> pageCategory = categoryService.getAllDeleted(page);
        model.addAttribute("pageCategory", pageCategory);
        return "admin/category/category-restore";
    }

    @PostMapping("/restore/{id}")
    public String restore(@PathVariable Integer id,
                          RedirectAttributes redirectAttributes) {
        categoryService.restore(id);
        redirectAttributes.addFlashAttribute("mess", "Khôi phục dữ liệu thành công");
        return "redirect:/nova/category/view-restore";
    }

    @GetMapping("/search-restore")
    public String searchDelete(Model model, @RequestParam(required = false) String categoryNameSearch,
                               @RequestParam(defaultValue = "0") int page) {
        Page<Category> pageCategory = categoryService.searchDelete(categoryNameSearch, page);
        if ("".equals(categoryNameSearch) || categoryNameSearch.isEmpty()) {
            return "redirect:/nova/category/view-restore";
        }
        model.addAttribute("categoryNameSearch", categoryNameSearch);
        model.addAttribute("pageCategory", pageCategory);
        return "admin/category/category-restore";
    }
}
