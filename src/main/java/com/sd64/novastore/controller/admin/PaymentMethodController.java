package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.PaymentMethod;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.PaymentMethodService;
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

import java.util.List;

@Controller
@RequestMapping("/admin/payment-method")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private BillService billService;

    @GetMapping("/page")
    public String getAllPagination(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model){
        Page<PaymentMethod> pagePaymentMethod = paymentMethodService.getAll(page);
        List<Bill> listBill = billService.getAllBill();

        model.addAttribute("listBill", listBill);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagePaymentMethod.getTotalPages());
        model.addAttribute("totalItems", pagePaymentMethod.getTotalElements());
        model.addAttribute("pagePaymentMethod", pagePaymentMethod.getContent());
        return "admin/payment-method/payment-method";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("PaymentMethod") PaymentMethod paymentMethod, RedirectAttributes redirectAttributes){
        paymentMethodService.add(paymentMethod);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/admin/payment-method/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        paymentMethodService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/admin/payment-method/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @Validated @ModelAttribute("PaymentMethod") PaymentMethod paymentMethod,
                         RedirectAttributes redirectAttributes){
        paymentMethodService.update(paymentMethod, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/admin/payment-method/page";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model){
        PaymentMethod paymentMethod = paymentMethodService.getOne(id);
        List<Bill> listBill = billService.getAllBill();

        model.addAttribute("listBill", listBill);
        model.addAttribute("paymentMethod", paymentMethod);
        return "admin/payment-method/payment-method-detail";
    }
}
