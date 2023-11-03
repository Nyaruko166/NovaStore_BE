package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.Voucher;
import com.sd64.novastore.model.VoucherDetail;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.VoucherDetailService;
import com.sd64.novastore.service.VoucherService;
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

import java.util.List;

@Controller
@RequestMapping("/admin/voucher_detail")
public class VoucherDetailController {
    @Autowired
    private VoucherDetailService voucherDetailService;

    @Autowired
    private BillService billService;

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        VoucherDetail voucherDetail = voucherDetailService.getOne(id);
        model.addAttribute("voucherDetail", voucherDetail);
        List<Voucher> voucherList = voucherService.getAll();
        List<Bill> billList = billService.getAllBill();
        model.addAttribute("voucherList", voucherList);
        model.addAttribute("billList", billList);
        return "/admin/voucherdetail/voucherdetail-detail";
    }

    @GetMapping("/page")
    public String getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<VoucherDetail> page1 = voucherDetailService.getAllPT(page);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", page1.getTotalPages());
//        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("page", page1);
        List<Voucher> voucherList = voucherService.getAll();
        List<Bill> billList = billService.getAllBill();
        model.addAttribute("voucherList", voucherList);
        model.addAttribute("billList", billList);
        return "/admin/voucherdetail/voucherdetail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        voucherDetailService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/admin/voucher_detail/page";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("VoucherDetail") VoucherDetail voucherDetail, RedirectAttributes redirectAttributes) {
        voucherDetailService.add(voucherDetail);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/admin/voucher_detail/page";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("VoucherDetail") VoucherDetail voucherDetail, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        voucherDetailService.update(voucherDetail, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/admin/voucher_detail/page";
    }
}
