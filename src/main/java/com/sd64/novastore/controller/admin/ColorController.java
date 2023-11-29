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

    @GetMapping("/product/{productId}/color/page")
    public String getPage(@PathVariable Integer productId,
                          @RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Color> pageColor = colorService.getPage(page);
        model.addAttribute("pageColor", pageColor);
        model.addAttribute("page", page);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        return "admin/color/color";
    }

    @GetMapping("/product/{productId}/color/detail/{id}")
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
        if (colorService.add(name)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/product/" + productId + "/color/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên màu sắc đã tồn tại");
            return "redirect:/nova/product/" + productId + "/color/page";
        }
    }

    @PostMapping("/{productId}/color/update/{id}")
    public String update(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         @RequestParam String name,
                         RedirectAttributes redirectAttributes) {
        if (colorService.update(id, name)) {
            redirectAttributes.addFlashAttribute("mess", "Sửa dữ liệu thành công");
            return "redirect:/nova/product/" + productId + "/color/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên màu sắc đã tồn tại");
            return "redirect:/nova/product/" + productId + "/color/detail/" + id;
        }
    }

    @PostMapping("/{productId}/color/delete/{id}")
    public String delete(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        colorService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa dữ liệu thành công");
        return "redirect:/nova/product/" + productId + "/color/page";
    }

    @GetMapping("/product/{productId}/color/search")
    public String search(@PathVariable Integer productId,
                         Model model, @RequestParam(required = false) String colorNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Color> pageColor = colorService.search(colorNameSearch, page);
        if ("".equals(colorNameSearch) || colorNameSearch.isEmpty()) {
            return "redirect:/nova/product/" + productId + "/color/page";
        }
        model.addAttribute("colorNameSearch", colorNameSearch);
        model.addAttribute("pageColor", pageColor);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        return "admin/color/color";
    }

    @GetMapping("/product/{productId}/color/view-restore")
    public String viewRestore(@PathVariable Integer productId,
                              @RequestParam(defaultValue = "0") int page,
                              Model model) {
        Page<Color> pageColor = colorService.getAllSizeDelete(page);
        model.addAttribute("pageColor", pageColor);
        model.addAttribute("productId", productId);
        return "admin/color/color-restore";
    }

    @PostMapping("/{productId}/color/restore/{id}")
    public String restore(@PathVariable Integer productId,
                          @PathVariable Integer id,
                          RedirectAttributes redirectAttributes) {
        colorService.restore(id);
        redirectAttributes.addFlashAttribute("mess", "Khôi phục dữ liệu thành công");
        return "redirect:/nova/product/" + productId +  "/color/view-restore";
    }

    @GetMapping("/product/{productId}/color/search-restore")
    public String searchDelete(@PathVariable Integer productId,
                               Model model, @RequestParam(required = false) String colorNameSearch,
                               @RequestParam(defaultValue = "0") int page) {
        Page<Color> pageColor = colorService.searchDeleted(colorNameSearch, page);
        if ("".equals(colorNameSearch) || colorNameSearch.isEmpty()) {
            return "redirect:/nova/product/" + productId +  "/color/view-restore";
        }
        model.addAttribute("colorNameSearch", colorNameSearch);
        model.addAttribute("pageColor", pageColor);
        return "admin/color/color-restore";
    }
}
