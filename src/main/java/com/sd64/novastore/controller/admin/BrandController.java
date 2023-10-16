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
        return "";
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(brandService.delete(id));
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Brand") Brand brand) {
        brandService.add(brand);
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
