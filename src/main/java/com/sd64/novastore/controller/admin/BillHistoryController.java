package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillHistory;
import com.sd64.novastore.service.AccountService;
import com.sd64.novastore.service.BillHistoryService;
import com.sd64.novastore.service.BillService;
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
@RequestMapping("/admin/bill-history")
public class BillHistoryController {
    @Autowired
    private BillHistoryService billHistoryService;

    @Autowired
    private BillService billService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/page")
    public String getAllPagination(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model){
        Page<BillHistory> pageBillHistory = billHistoryService.getAll(page);
        List<Bill> listBill = billService.getAllBill();
        List<Account> listAccount = accountService.getAll();

        model.addAttribute("listBill", listBill);
        model.addAttribute("listAccount", listAccount);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageBillHistory.getTotalPages());
        model.addAttribute("totalItems", pageBillHistory.getTotalElements());
        model.addAttribute("pageBillHistory", pageBillHistory.getContent());
        return "admin/bill-history/bill-history";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("billHistory") BillHistory billHistory,
                      RedirectAttributes redirectAttributes){
        billHistoryService.add(billHistory);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/admin/bill-history/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        billHistoryService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/admin/bill-history/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @Validated @ModelAttribute("billHistory") BillHistory billHistory,
                         RedirectAttributes redirectAttributes){
        billHistoryService.update(billHistory, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/admin/bill-history/page";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model){
        BillHistory billHistory = billHistoryService.getOne(id);
        List<Bill> listBill = billService.getAllBill();
        List<Account> listAccount = accountService.getAll();

        model.addAttribute("listBill", listBill);
        model.addAttribute("listAccount", listAccount);
        model.addAttribute("billHistory", billHistory);
        return "admin/bill-history/bill-history-detail";
    }
}
