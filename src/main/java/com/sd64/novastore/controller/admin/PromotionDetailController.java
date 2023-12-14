package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.PromotionDetail;
import com.sd64.novastore.service.ProductDetailService;
import com.sd64.novastore.service.ProductService;
import com.sd64.novastore.service.PromotionDetailService;
import com.sd64.novastore.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/nova/promotion-detail")
public class PromotionDetailController {
    @Autowired
    private PromotionDetailService promotionDetailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PromotionService promotionService;


    @GetMapping("/page")
    public String getAllPTPagination(@ModelAttribute("promotionDetail") PromotionDetail promotionDetail, @RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<PromotionDetail> pagePromotionDetail = promotionDetailService.getAllPT(page);
        model.addAttribute("pagePromotionDetail", pagePromotionDetail);
        model.addAttribute("page", page);
        List<Promotion> promotionList = promotionService.getAll();
        List<Product> productList = promotionDetailService.getAll();
        List<ProductDetail> productDetailList= promotionDetailService.getAllPrDT();
        model.addAttribute("productDetailList", productDetailList);
        model.addAttribute("promotionList", promotionList);
        model.addAttribute("productList", productList);
        return "admin/promotiondetail/promotiondetail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        PromotionDetail promotionDetail = promotionDetailService.getOne(id);

        if (promotionDetail != null) {
            Product product = promotionDetail.getProduct();
            promotionDetailService.delete(id);
            product.setStatus(1);
            promotionDetailService.save(product);
            redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        }
        return "redirect:/nova/promotion-detail/page";
    }


    @PostMapping("/add")
    public String add(Model model, @Valid @ModelAttribute("promotionDetail") PromotionDetail promotionDetail,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes,
                      @RequestParam(name = "page", defaultValue = "0") Integer page,
                      @RequestParam("selectedProducts") List<Integer> selectedProducts,
                      @RequestParam("promotion") Integer promotionId) {

        if (bindingResult.hasErrors()) {
            Page<PromotionDetail> pagePromotionDetail = promotionDetailService.getAllPT(page);
            model.addAttribute("pagePromotionDetail", pagePromotionDetail);
            model.addAttribute("page", page);
            List<Promotion> promotionList = promotionService.getAll();
            List<Product> productList = promotionDetailService.getAll();
            model.addAttribute("promotionList", promotionList);
            model.addAttribute("productList", productList);
            List<ProductDetail> productDetailList= promotionDetailService.getAllPrDT();
            model.addAttribute("productDetailList", productDetailList);
            return "admin/promotiondetail/promotiondetail";
        }

        Promotion promotion = promotionService.getOne(promotionId);

        for (Integer productId : selectedProducts) {
            Product product = productService.getOne(productId);
            product.setStatus(2);
            promotionDetailService.save(product);
            PromotionDetail newPromotionDetail = new PromotionDetail();
            newPromotionDetail.setProduct(product);
            newPromotionDetail.setPromotion(promotion);
            promotionDetailService.add(newPromotionDetail);
        }

        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/nova/promotion-detail/page";
    }

}
