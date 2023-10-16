package com.sd64.novastore.controller;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.request.ColorRequest;
import com.sd64.novastore.service.ColorService;
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
@RequestMapping("/admin/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Color> listColor = colorService.getAll();
        model.addAttribute("listColor", listColor);
        return "";
    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Color> pageColor = colorService.getPage(page);
        model.addAttribute("pageColor", pageColor.getContent());
        return "";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Color") Color color) {
        colorService.add(color);
        return "redirect:/admin/color/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute("Color") Color color) {
        colorService.update(color, id);
        return "redirect:/admin/color/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        colorService.delete(id);
        return "redirect:/admin/color/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String nameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Color> listColor = colorService.search(nameSearch, page);
        model.addAttribute("listColor", listColor.getContent());
        return "";
    }
}
