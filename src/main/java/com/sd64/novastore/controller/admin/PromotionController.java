package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/page")
    public String getAllPagination(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                   Model model) {
        Page<Promotion> pagePromotion = promotionService.getAllPT(page);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagePromotion.getTotalPages());
        model.addAttribute("totalItems", pagePromotion.getTotalElements());
        model.addAttribute("pagePromotion", pagePromotion.getContent());
        return "admin/promotion/promotion";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("promotion") Promotion promotion, RedirectAttributes redirectAttributes) {
        promotionService.add(promotion);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/admin/promotion/page";
    }

    @PostMapping("/update/{id}")
    public String update(@Validated @PathVariable Integer id, @ModelAttribute("promotion") Promotion promotion,
                         RedirectAttributes redirectAttributes) {
        promotionService.update(promotion, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/admin/promotion/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        promotionService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/admin/promotion/page";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Promotion promotion = promotionService.getOne(id);
        model.addAttribute("promotion", promotion);
        return "admin/promotion/promotion-detail";
    }
}
