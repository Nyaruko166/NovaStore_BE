package com.sd64.novastore.controller.admin;

import com.sd64.novastore.dto.ProductDetailDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{productId}")
    public String getProductDetail(@PathVariable Integer productId, Model model, @RequestParam(defaultValue = "0") int page) {
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


    @PostMapping("/add")
    public String add(@RequestParam Integer productId,
                      @RequestParam Integer quantity,
                      @RequestParam Integer sizeId,
                      @RequestParam Integer colorId,
                      RedirectAttributes redirectAttributes) {
        productDetailService.add(productId, quantity, sizeId, colorId);
        redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành cồng");
        return "redirect:/nova/product-detail/" + productId;
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        ProductDetail productDetail = productDetailService.getOne(id);
        model.addAttribute("productDetail", productDetail);
        List<Size> listSize = sizeService.getAll();
        List<Color> listColor = colorService.getAll();
        model.addAttribute("listSize", listSize);
        model.addAttribute("listColor", listColor);
        return "admin/product-detail/product-detail-update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @RequestParam Integer productId,
                         @RequestParam Integer quantity,
                         @RequestParam Integer sizeId,
                         @RequestParam Integer colorId,
                         RedirectAttributes redirectAttributes) {
        productDetailService.update(id, productId, quantity, sizeId, colorId);
        redirectAttributes.addFlashAttribute("mess", "Sửa dữ liệu thành công");
        return "redirect:/nova/product-detail/" + productId;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        ProductDetail productDetail = productDetailService.getOne(id);
        productDetailService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa dữ liệu thành công");
        return "redirect:/nova/product-detail/" + productDetail.getProduct().getId();
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Integer productId,
                         @RequestParam(required = false) Integer quantity,
                         @RequestParam(required = false) Integer sizeId,
                         @RequestParam(required = false) Integer colorId,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Page<ProductDetailDto> pageProductDetailDto = productDetailService.getProductBySizeIdAndColorId(page, productId, quantity, sizeId, colorId);
        List<Size> listSize = sizeService.getAll();
        List<Color> listColor = colorService.getAll();
        model.addAttribute("listSize", listSize);
        model.addAttribute("listColor", listColor);
        model.addAttribute("quantity", quantity);
        model.addAttribute("sizeId", sizeId);
        model.addAttribute("colorId", colorId);
        model.addAttribute("pageProductDetail", pageProductDetailDto);
        return "/admin/product-detail/product-detail";
    }
}
