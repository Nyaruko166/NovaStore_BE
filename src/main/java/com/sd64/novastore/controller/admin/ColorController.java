package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.service.ColorService;
import com.sd64.novastore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nova")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}/color/page")
    public String getPage(@PathVariable Integer productId,
                          @RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Color> pageColor = colorService.getPage(page);
        model.addAttribute("pageColor", pageColor);
        model.addAttribute("page", page);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        return "admin/color/color";
    }

    @GetMapping("/{productId}/color/detail/{id}")
    public String detail(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         Model model) {
        Color color = colorService.detail(id);
        model.addAttribute("color", color);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        model.addAttribute("productId", productId);
        return "/admin/color/color-detail";
    }

    @PostMapping("/color/add")
    public String add(@RequestParam Integer productId,
                      @RequestParam String name,
                      RedirectAttributes redirectAttributes) {
        colorService.add(name);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
        return "redirect:/nova/" + productId + "/color/page";
    }

    @PostMapping("/{productId}/color/update/{id}")
    public String update(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         @RequestParam String name,
                         RedirectAttributes redirectAttributes) {
        colorService.update(id, name);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công");
        return "redirect:/nova/" + productId + "/color/page";
    }

    @PostMapping("/{productId}/color/delete/{id}")
    public String delete(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        colorService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa thành công");
        return "redirect:/nova/" + productId + "/color/page";
    }

    @GetMapping("{productId}/color/search")
    public String search(@PathVariable Integer productId,
                         Model model, @RequestParam(required = false) String colorNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Color> pageColor = colorService.search(colorNameSearch, page);
        if ("".equals(colorNameSearch) || colorNameSearch.isEmpty()) {
            return "redirect:/nova/color/page";
        }
        model.addAttribute("colorNameSearch", colorNameSearch);
        model.addAttribute("pageColor", pageColor);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        return "admin/color/color";
    }
}
