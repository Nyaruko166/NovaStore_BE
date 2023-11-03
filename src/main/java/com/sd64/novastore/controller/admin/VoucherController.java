package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Voucher;
import com.sd64.novastore.service.VoucherService;
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
@RequestMapping("/nova/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/page")
    public String getAllPagination(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                   Model model){
        Page<Voucher> pageVoucher = voucherService.getAll(page);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageVoucher.getTotalPages());
        model.addAttribute("totalItems", pageVoucher.getTotalElements());
        model.addAttribute("pageVoucher", pageVoucher.getContent());
        return "admin/voucher/voucher";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("voucher") Voucher voucher, RedirectAttributes redirectAttributes){
        voucherService.add(voucher);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/nova/voucher/page";
    }

    @PostMapping("/update/{id}")
    public String update(@Validated @PathVariable Integer id, @ModelAttribute("voucher") Voucher voucher,
                      RedirectAttributes redirectAttributes){
        voucherService.update(voucher, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/nova/voucher/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        voucherService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/nova/voucher/page";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model){
        Voucher voucher = voucherService.getOne(id);
        model.addAttribute("voucher", voucher);
        return "admin/voucher/voucher-detail";
    }
}
