package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.service.ColorService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/nova/product-detail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private SizeService sizeService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private ProductService productService;

    private Product productRedirect;

    @GetMapping("/{productId}")
    public String detail(@PathVariable Integer productId, Model model, @RequestParam(defaultValue = "0") int page) {
        List<Size> listSize = sizeService.getAll();
        List<Color> listColor = colorService.getAll();
        Page<ProductDetail> pageProductDetail = productDetailService.getProductDetailByProductId(page, productId);
        model.addAttribute("pageProductDetail", pageProductDetail);
        Product product = productService.getOne(productId);
        model.addAttribute("productId", productId);
        model.addAttribute("product", product);
        model.addAttribute("listSize", listSize);
        model.addAttribute("listColor", listColor);
        return "admin/product-detail/product-detail";
    }

    @GetMapping("/view-add/{productId}")
    public String viewAdd(Model model, @PathVariable Integer productId) {
        List<Size> listSize = sizeService.getAll();
        List<Color> listColor = colorService.getAll();
        Product product = productService.getOne(productId);
        ProductDetail productDetail = new ProductDetail();
        model.addAttribute("product", product);
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("listSize", listSize);
        model.addAttribute("listColor", listColor);
        return "admin/product-detail/product-detail-add";
    }

    @GetMapping("/by-productId")
    public String getProductDetailByProductId(Model model, @RequestParam(defaultValue = "0") int page, RedirectAttributes redirectAttributes) {
        Page<ProductDetail> pageProductDetail = productDetailService.getProductDetailByProductId(page, this.productRedirect.getId());
        model.addAttribute("pageProductDetail", pageProductDetail);
        redirectAttributes.addAttribute("mess", "Thêm dữ liệu thành công");
        return "/admin/product-detail/product-detail";
    }

    @PostMapping("/add")
    public String add(@RequestParam Integer productId,
                      @RequestParam Integer quantity,
                      @RequestParam Integer sizeId,
                      @RequestParam Integer colorId) {
        productDetailService.add(productId, quantity, sizeId, colorId);
        return "redirect:/nova/product-detail/" + productId;
    }

}
