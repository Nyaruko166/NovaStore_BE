package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.PromotionDetail;
import com.sd64.novastore.service.ProductService;
import com.sd64.novastore.service.PromotionDetailService;
import com.sd64.novastore.service.PromotionService;
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
@RequestMapping("/admin/promotion_detail")
public class PromotionDetailController {
    @Autowired
    private PromotionDetailService promotionDetailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        PromotionDetail promotionDetail = promotionDetailService.getOne(id);
        model.addAttribute("promotionDetail", promotionDetail);
        List<Promotion> promotionList = promotionService.getAll();
        List<Product> productList = productService.getAll();
        model.addAttribute("promotionList", promotionList);
        model.addAttribute("productList", productList);
        return "/admin/promotiondetail/promotiondetail-detail";
    }

    @GetMapping("/page")
    public String getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<PromotionDetail> page1 = promotionDetailService.getAllPT(page);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("page", page1.getContent());
        List<Promotion> promotionList = promotionService.getAll();
        List<Product> productList = productService.getAll();
        model.addAttribute("promotionList", promotionList);
        model.addAttribute("productList", productList);
        return "/admin/promotiondetail/promotiondetail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        promotionDetailService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/admin/promotion_detail/page";
    }


    @PostMapping("/add")
    public String add( @ModelAttribute("PromotionDetail") PromotionDetail promotionDetail, RedirectAttributes redirectAttributes) {
        promotionDetailService.add(promotionDetail);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/admin/promotion_detail/page";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("PromotionDetail") PromotionDetail promotionDetail, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        promotionDetailService.update(promotionDetail, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/admin/promotion_detail/page";

    }
}
