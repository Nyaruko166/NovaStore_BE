package com.sd64.novastore.controller;

import com.sd64.novastore.model.Form;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.request.MaterialRequest;
import com.sd64.novastore.service.MaterialService;
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
@RequestMapping("/admin/material")
@CrossOrigin
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Material> listMaterial = materialService.getAll();
        model.addAttribute("listMaterial", listMaterial);
        return "";
    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Material> pageMaterial = materialService.getPage(page);
        model.addAttribute("pageMaterial", pageMaterial.getContent());
        return "";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Material") Material material) {
        materialService.add(material);
        return "redirect:/admin/material/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute("Material") Material material) {
        materialService.update(material, id);
        return "redirect:/admin/material/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        materialService.delete(id);
        return "redirect:/admin/material/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String nameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Material> listMaterial = materialService.search(nameSearch, page);
        model.addAttribute("listMaterial", listMaterial.getContent());
        return "";
    }
}
