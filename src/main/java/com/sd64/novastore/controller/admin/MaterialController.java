package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Form;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

//    @GetMapping("/all")
//    public String getAll(Model model) {
//        List<Material> listMaterial = materialService.getAll();
//        model.addAttribute("listMaterial", listMaterial);
//        return "";
//    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Material> pageMaterial = materialService.getPage(page);
        model.addAttribute("pageMaterial", pageMaterial);
        model.addAttribute("page", page);
        return "admin/material/material";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Material material = materialService.detail(id);
        model.addAttribute("material", material);
        return "/admin/material/material-detail";
    }

    @PostMapping("/add")
    public String add( @ModelAttribute Material material, RedirectAttributes redirectAttributes) {
        materialService.add(material);
        redirectAttributes.addFlashAttribute("add", "Thêm thành công");
        return "redirect:/admin/material/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Material material, RedirectAttributes redirectAttributes) {
        materialService.update(material, id);
        redirectAttributes.addFlashAttribute("update", "Sửa thành công");
        return "redirect:/admin/material/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        materialService.delete(id);
        redirectAttributes.addFlashAttribute("delete", "Xóa thành công");
        return "redirect:/admin/material/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String materialNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Material> pageMaterial = materialService.search(materialNameSearch, page);
        if ("".equals(materialNameSearch) || materialNameSearch.isEmpty()) {
            return "redirect:/admin/material/page";
        }
        model.addAttribute("materialNameSearch", materialNameSearch);
        model.addAttribute("pageMaterial", pageMaterial);
        return "admin/material/material";
    }
}
