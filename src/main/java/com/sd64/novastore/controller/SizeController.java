package com.sd64.novastore.controller;

import com.sd64.novastore.model.Role;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.request.PromotionRequest;
import com.sd64.novastore.request.SizeRequest;
import com.sd64.novastore.service.PromotionService;
import com.sd64.novastore.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/size")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Size> listSize = sizeService.getAll();
        model.addAttribute("listSize", listSize);
        return "";
    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Size> pageSize = sizeService.getPage(page);
        model.addAttribute("pageSize", pageSize.getContent());
        return "";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Size") Size size) {
        sizeService.add(size);
        return "redirect:/admin/size/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute("Size") Size size) {
        sizeService.update(size, id);
        return "redirect:/admin/size/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        sizeService.delete(id);
        return "redirect:/admin/size/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String nameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Size> listSize = sizeService.search(nameSearch, page);
        model.addAttribute("listSize", listSize.getContent());
        return "";
    }
}
