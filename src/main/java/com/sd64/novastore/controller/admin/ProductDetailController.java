package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.service.ProductDetailService;
import com.sd64.novastore.service.ProductService;
import com.sd64.novastore.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/product_detail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductService productService;

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        ProductDetail productDetail = productDetailService.getOne(id);
        List<Size> sizeList = sizeService.getAll();
//        List<Product> productList = productService.getAll();
//        model.addAttribute("cartList", cartList);
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("sizeList", sizeList);
        return "/admin/productdetail/productdetail-detail";
    }

    @GetMapping("/page")
    public String getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<ProductDetail> page1 = productDetailService.getAllPT(page);
        model.addAttribute("page", page1);
        return "/admin/productdetail/productdetail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        productDetailService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/admin/product_detail/page";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("ProductDetail") ProductDetail productDetail, RedirectAttributes redirectAttributes) {
        productDetailService.add(productDetail);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/admin/product_detail/page";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("ProductDetail") ProductDetail productDetail, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        productDetailService.update(productDetail, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/admin/product_detail/page";

    }
}
