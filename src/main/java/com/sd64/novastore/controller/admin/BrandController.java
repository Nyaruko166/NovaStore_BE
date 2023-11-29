package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        redirectAttributes.addFlashAttribute("mess", "Xóa dữ liệu thành công");
        return "redirect:/nova/brand/page";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Brand brand,
                      RedirectAttributes redirectAttributes) {
        if (brandService.add(brand)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/brand/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên thương hiệu đã tồn tại");
            return "redirect:/nova/brand/page";
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @RequestParam String name,
                         RedirectAttributes redirectAttributes) {
        if (brandService.update(id, name)) {
            redirectAttributes.addFlashAttribute("mess", "Sửa thành công");
            return "redirect:/nova/brand/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên thương hiệu đã tồn tại");
            return "redirect:/nova/brand/detail/" + id;
        }
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
    public String viewRestore(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Brand> pageBrand = brandService.getAllDeleted(page);
        model.addAttribute("pageBrand", pageBrand);
        return "admin/brand/brand-restore";
    }

    @GetMapping("/search-restore")
    public String searchDelete(Model model, @RequestParam(required = false) String brandNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Brand> pageBrand = brandService.searchDelete(brandNameSearch, page);
        if ("".equals(brandNameSearch) || brandNameSearch.isEmpty()) {
            return "redirect:/nova/brand/view-restore";
        }
        model.addAttribute("brandNameSearch", brandNameSearch);
        model.addAttribute("pageBrand", pageBrand);
        return "admin/brand/brand-restore";
    }

    @PostMapping("/restore/{id}")
    public String restore(@PathVariable Integer id,
                          RedirectAttributes redirectAttributes) {
        brandService.restore(id);
        redirectAttributes.addFlashAttribute("mess", "Khôi phục dữ liệu thành công");
        return "redirect:/nova/brand/view-restore";
    }
}
