package com.sd64.novastore.controller.admin;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.dto.admin.PromotionDetailDTO;
import com.sd64.novastore.dto.admin.thongke.PromotionSearchDTO;
import com.sd64.novastore.dto.admin.thongke.TKKhoangNgay;
import com.sd64.novastore.dto.admin.thongke.TKNam;
import com.sd64.novastore.dto.admin.thongke.TKNgay;
import com.sd64.novastore.dto.admin.thongke.TKSanPham;
import com.sd64.novastore.dto.admin.thongke.TKThang;
import com.sd64.novastore.dto.admin.thongke.TKTrangThaiHoaDon;
import com.sd64.novastore.dto.admin.thongke.TKTuan;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/nova/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/page")
    public String getAllPagination(@ModelAttribute("promotion") Promotion promotion, @RequestParam(name = "page", defaultValue = "0") Integer page,
                                   Model model) {
        Page<Promotion> pagePromotion = promotionService.getAllPT(page);
        model.addAttribute("pagePromotion", pagePromotion);
        model.addAttribute("page", page);
        return "admin/promotion/promotion";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("promotion") Promotion promotion, RedirectAttributes redirectAttributes) {
        promotionService.add(promotion);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/nova/promotion/page";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("promotion") Promotion promotion, @PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        promotionService.update(promotion, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/nova/promotion/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        promotionService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/nova/promotion/page";

    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Promotion promotion = promotionService.getOne(id);
        model.addAttribute("promotion", promotion);
        return "admin/promotion/promotion-detail";
    }

    @GetMapping("/promotionChiTiet/{id}")
    public String detailCT(@PathVariable Integer id, Model model) {
        Promotion promotion = promotionService.getOne(id);
        model.addAttribute("promotion", promotion);
        List<PromotionDetailDTO> promotionDetails = promotionService.getPromotionDetailsByPromotionId(id);
        model.addAttribute("promotionDetails", promotionDetails);
        return "admin/promotion/promotionChiTiet";
    }


    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String code,
            @RequestParam(name = "ngayTaoStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoStart,
            @RequestParam(name = "ngayTaoEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoEnd,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String name,
            Model model, @RequestParam(defaultValue = "0") int page) {

        if ((code == null || code.isEmpty()) && status == null && (name == null || name.isEmpty()) && ngayTaoStart == null && ngayTaoEnd == null) {
            return "redirect:/nova/promotion/page";
        }
            Page<PromotionSearchDTO> promotionSearchDTOPage = promotionService.searchPromotion(code, ngayTaoStart, ngayTaoEnd, status, name, page);
            model.addAttribute("pagePromotion", promotionSearchDTOPage);
        model.addAttribute("code", code);
        model.addAttribute("status", status);
        model.addAttribute("name", name);
        model.addAttribute("ngayTaoStart", ngayTaoStart);
        model.addAttribute("ngayTaoEnd", ngayTaoEnd);

        return "admin/promotion/promotion";
    }


}
