package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Material;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.request.PromotionRequest;
import com.sd64.novastore.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Promotion> listPromotion = promotionService.getAll();
        model.addAttribute("listPromotion", listPromotion);
        return "";
    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Promotion> pagePromotion = promotionService.getPage(page);
        model.addAttribute("pagePromotion", pagePromotion.getContent());
        return "";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Promotion") Promotion promotion) {
        promotionService.add(promotion);
        return "redirect:/admin/promotion/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute("Promotion") Promotion promotion) {
        promotionService.update(promotion, id);
        return "redirect:/admin/promotion/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        promotionService.delete(id);
        return "redirect:/admin/promotion/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String nameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Promotion> listPromotion = promotionService.search(nameSearch, page);
        model.addAttribute("listPromotion", listPromotion.getContent());
        return "";
    }
}
