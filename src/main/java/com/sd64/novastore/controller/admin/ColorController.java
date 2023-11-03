package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/nova/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Color> pageColor = colorService.getPage(page);
        model.addAttribute("pageColor", pageColor);
        model.addAttribute("page", page);
        return "admin/color/color";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Color color = colorService.detail(id);
        model.addAttribute("color", color);
        return "/admin/color/color-detail";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Color color, RedirectAttributes redirectAttributes) {
        colorService.add(color);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
        return "redirect:/nova/color/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute Color color, RedirectAttributes redirectAttributes) {
        colorService.update(color, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công");
        return "redirect:/nova/color/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        colorService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa thành công");
        return "redirect:/nova/color/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String colorNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Color> pageColor = colorService.search(colorNameSearch, page);
        if ("".equals(colorNameSearch) || colorNameSearch.isEmpty()) {
            return "redirect:/nova/color/page";
        }
        model.addAttribute("colorNameSearch", colorNameSearch);
        model.addAttribute("pageColor", pageColor);
        return "admin/color/color";
    }
}
