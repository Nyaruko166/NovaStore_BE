package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.Size;
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

    @GetMapping("/product/{productId}/size/page")
    public String getPage(@PathVariable Integer productId,
                          @RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Size> pageSize = sizeService.getPage(page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        return "admin/size/size";
    }

    @GetMapping("/product/{productId}/size/detail/{id}")
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
        if (sizeService.add(name)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/product/" + productId + "/size/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Kích cỡ đã tồn tại");
            return "redirect:/nova/product/" + productId + "/size/page";
        }
    }

    @PostMapping("/{productId}/size/update/{id}")
    public String update(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         @RequestParam String name,
                         RedirectAttributes redirectAttributes) {
        if (sizeService.update(id, name)) {
            redirectAttributes.addFlashAttribute("mess", "Sửa dữ liệu thành công");
            return "redirect:/nova/product/" + productId + "/size/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên kích cỡ đã tồn tại");
            return "redirect:/nova/product/" + productId + "/size/detail/" + id;
        }
    }

    @PostMapping("/{productId}/size/delete/{id}")
    public String delete(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        sizeService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa dữ liệu thành công");
        return "redirect:/nova/product/" + productId + "/size/page";
    }

    @GetMapping("/product/{productId}/size/search")
    public String search(@PathVariable Integer productId,
                         Model model, @RequestParam(required = false) String sizeNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Size> pageSize = sizeService.search(sizeNameSearch, page);
        if ("".equals(sizeNameSearch) || sizeNameSearch.isEmpty()) {
            return "redirect:/nova/product/" + productId + "/size/page";
        }
        model.addAttribute("sizeNameSearch", sizeNameSearch);
        model.addAttribute("pageSize", pageSize);
        Product product = productService.getOne(productId);
        model.addAttribute("product", product);
        return "admin/size/size";
    }

    @GetMapping("/product/{productId}/size/view-restore")
    public String viewRestore(@PathVariable Integer productId,
                              @RequestParam(defaultValue = "0") int page,
                              Model model) {
        Page<Size> pageSize = sizeService.getAllSizeDelete(page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("productId", productId);
        return "admin/size/size-restore";
    }

    @PostMapping("/{productId}/size/restore/{id}")
    public String restore(@PathVariable Integer productId,
                          @PathVariable Integer id,
                          RedirectAttributes redirectAttributes) {
        sizeService.restore(id);
        redirectAttributes.addFlashAttribute("mess", "Khôi phục dữ liệu thành công");
        return "redirect:/nova/product/" + productId +  "/size/view-restore";
    }

    @GetMapping("/product/{productId}/size/search-restore")
    public String searchDelete(@PathVariable Integer productId,
                               Model model, @RequestParam(required = false) String sizeNameSearch,
                               @RequestParam(defaultValue = "0") int page) {
        Page<Size> pageSize = sizeService.searchDeleted(sizeNameSearch, page);
        if ("".equals(sizeNameSearch) || sizeNameSearch.isEmpty()) {
            return "redirect:/nova/product/" + productId +  "/size/view-restore";
        }
        model.addAttribute("sizeNameSearch", sizeNameSearch);
        model.addAttribute("pageSize", pageSize);
        return "admin/size/size-restore";
    }
}
