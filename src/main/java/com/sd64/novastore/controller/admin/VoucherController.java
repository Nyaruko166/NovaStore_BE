package com.sd64.novastore.controller.admin;

import com.sd64.novastore.dto.admin.thongke.VoucherSearchDTO;
import com.sd64.novastore.model.Voucher;
import com.sd64.novastore.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import java.util.Date;

@Controller
@RequestMapping("/nova/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/page")
    public String getAllPagination(@ModelAttribute("voucher") Voucher voucher, @RequestParam(name = "page", defaultValue = "0") Integer page,
                                   Model model) {
        Page<Voucher> pageVoucher = voucherService.getAll(page);
        model.addAttribute("pageVoucher", pageVoucher);
        model.addAttribute("page", page);
        return "admin/voucher/voucher";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        Voucher voucher = new Voucher();
        model.addAttribute("voucher", voucher);
        return "admin/voucher/voucher-add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("voucher") Voucher voucher, RedirectAttributes redirectAttributes) {
        if (voucherService.add(voucher)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/voucher/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên voucher đã tồn tại");
            return "redirect:/nova/voucher/page";
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("voucher") Voucher voucher, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if(voucherService.update(voucher, id)) {
            redirectAttributes.addFlashAttribute("mess", "Sửa dữ liệu thành công");
            return "redirect:/nova/voucher/page";
        }else {
            redirectAttributes.addFlashAttribute("error", "Tên voucher đã tồn tại");
            return "redirect:/nova/voucher/page";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        voucherService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/nova/voucher/page";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Voucher voucher = voucherService.getOne(id);
        model.addAttribute("voucher", voucher);
        return "admin/voucher/voucher-detail";
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
            return "redirect:/nova/voucher/page";
        }
        Page<VoucherSearchDTO> voucherSearchDTOPage = voucherService.searchVoucher(code, ngayTaoStart, ngayTaoEnd, status, name, page);
        model.addAttribute("pageVoucher", voucherSearchDTOPage);
        model.addAttribute("code", code);
        model.addAttribute("status", status);
        model.addAttribute("name", name);
        model.addAttribute("ngayTaoStart", ngayTaoStart);
        model.addAttribute("ngayTaoEnd", ngayTaoEnd);
        return "admin/voucher/voucher";
    }
}
