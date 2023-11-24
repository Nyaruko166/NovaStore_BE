package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nova/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Brand> pageBrand = brandService.getPage(page);
        model.addAttribute("pageBrand", pageBrand);
        model.addAttribute("page", page);
        return "admin/brand/brand";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Brand brand = brandService.detail(id);
        model.addAttribute("brand", brand);
        return "/admin/brand/brand-detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        brandService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa thành công");
        return "redirect:/nova/brand/page";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute Brand brand,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model) {
        brandService.add(brand);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
        return "redirect:/nova/brand/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Brand brand,
                         RedirectAttributes redirectAttributes) {
        brandService.update(brand, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công");
        return "redirect:/nova/brand/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String brandNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Brand> pageBrand = brandService.search(brandNameSearch, page);
        if ("".equals(brandNameSearch) || brandNameSearch.isEmpty()) {
            return "redirect:/nova/brand/page";
        }
        model.addAttribute("brandNameSearch", brandNameSearch);
        model.addAttribute("pageBrand", pageBrand);
        return "admin/brand/brand";
    }

    @GetMapping("/view-restore")
    public String getBrandRestore(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Brand> pageBrand = brandService.getAllBrandDeleted(page);
        model.addAttribute("pageBrand", pageBrand);
        model.addAttribute("page", page);
        return "admin/brand/brand-restore";
    }

    @GetMapping("/search-restore")
    public String searchBrandDelete(Model model, @RequestParam(required = false) String brandNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Brand> pageBrand = brandService.search(brandNameSearch, page);
        if ("".equals(brandNameSearch) || brandNameSearch.isEmpty()) {
            return "redirect:/nova/brand/view-restore";
        }
        model.addAttribute("brandNameSearch", brandNameSearch);
        model.addAttribute("pageBrand", pageBrand);
        return "admin/brand/brand-restore";
    }

    @PostMapping("/restore")
    public String restore() {
        return "";
    }
}
