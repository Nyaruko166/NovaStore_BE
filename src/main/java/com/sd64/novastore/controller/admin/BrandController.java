package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Brand> listBrand = brandService.getAll();
        model.addAttribute("listBrand", listBrand);
        return "";
    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Brand> pageBrand = brandService.getPage(page);
        model.addAttribute("pageBrand", pageBrand.getContent());
        return "admin/brand/brand";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Brand brand = brandService.detail(id);
        model.addAttribute("brand", brand);
        return "/admin/brand/brand-detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        brandService.delete(id);
        return "redirect:/admin/brand/page";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Brand") Brand brand, RedirectAttributes redirectAttributes) {
        brandService.add(brand);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
        return "redirect:/admin/brand/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute("Brand") Brand brand) {
        brandService.update(brand, id);
        return "redirect:/admin/brand/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String nameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Brand> listBrand = brandService.search(nameSearch, page);
        model.addAttribute("listBrand", listBrand.getContent());
        return "";
    }
}
