package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.service.ProductDetailService;
import com.sd64.novastore.service.ProductService;
import com.sd64.novastore.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/nova")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}/size/page")
    public String getPage(@PathVariable Integer productId,
                          @RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Size> pageSize = sizeService.getPage(page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        return "admin/size/size";
    }

    @GetMapping("/{productId}/size/detail/{id}")
    public String detail(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         Model model) {
        Size size = sizeService.detail(id);
        model.addAttribute("size", size);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        model.addAttribute("productId", productId);
        return "/admin/size/size-detail";
    }

    @PostMapping("/size/add")
    public String add(@RequestParam Integer productId,
                      @RequestParam String name,
                      RedirectAttributes redirectAttributes) {
        sizeService.add(name);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
        return "redirect:/nova/" + productId + "/size/page";
    }

    @PostMapping("/{productId}/size/update/{id}")
    public String update(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         @RequestParam String name,
                         RedirectAttributes redirectAttributes) {
        sizeService.update(id, name);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công");
        return "redirect:/nova/" + productId + "/size/page";
    }

    @PostMapping("/{productId}/size/delete/{id}")
    public String delete(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        sizeService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa thành công");
        return "redirect:/nova/" + productId + "/size/page";
    }

    @GetMapping("{productId}/size/search")
    public String search(@PathVariable Integer productId,
                         Model model, @RequestParam(required = false) String sizeNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Size> pageSize = sizeService.search(sizeNameSearch, page);
        if ("".equals(sizeNameSearch) || sizeNameSearch.isEmpty()) {
            return "redirect:/nova/size/page";
        }
        model.addAttribute("sizeNameSearch", sizeNameSearch);
        model.addAttribute("pageSize", pageSize);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        return "admin/size/size";
    }
}
