package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/nova/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Promotion> pagePromotion = promotionService.getPage(page);
        model.addAttribute("pagePromotion", pagePromotion);
        model.addAttribute("page", page);
        return "admin/promotion/promotion";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Promotion promotion = promotionService.detail(id);
        model.addAttribute("promotion", promotion);
        return "/admin/promotion/promotion-detail";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Promotion promotion, RedirectAttributes redirectAttributes) {
        promotionService.add(promotion);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
        return "redirect:/nova/promotion/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Promotion promotion, RedirectAttributes redirectAttributes) {
        promotionService.update(promotion, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công");
        return "redirect:/nova/promotion/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        promotionService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa thành công");
        return "redirect:/nova/promotion/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String promotionNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Promotion> pagePromotion = promotionService.search(promotionNameSearch, page);
        if ("".equals(promotionNameSearch) || promotionNameSearch.isEmpty()) {
            return "redirect:/nova/promotion/page";
        }
        model.addAttribute("promotionNameSearch", promotionNameSearch);
        model.addAttribute("pagePromotion", pagePromotion);
        return "admin/promotion/promotion";
    }
}
