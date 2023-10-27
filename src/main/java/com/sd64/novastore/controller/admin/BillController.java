package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping("/page")
    public String getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page,
                           Model model) {
        Page<Bill> page1 = billService.getAllBillPT(page);
        model.addAttribute("page", page1.getContent());
        for (Bill x : billService.getAllBill()) {

        }
        return "/admin/bill/bill";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id,
                         Model model) {
        List<BillDetail> lstBillDetails = billService.getLstDetailByBillId(id);
        model.addAttribute("page", lstBillDetails);
        return "/admin/bill/bill-detail";
    }
//    @PostMapping("/delete/{id}")
//    public String delete(@PathVariable Integer id) {
//        return String.ok(billService.delete(id));
//    }

//    @PostMapping("/add")
//    public String add(@RequestBody @Valid BillRequest billRequest, BindingResult result) {
//        if (result.hasErrors()) {
//            List<ObjectError> list = result.getAllErrors();
//            return String.ok(list);
//        }
//        return String.ok(billService.add(billRequest));
//    }
//
//    @PostMapping("/update/{id}")
//    public String update(@RequestBody @Valid BillRequest billRequest, @PathVariable Integer id, BindingResult result) {
//        if (result.hasErrors()) {
//            List<ObjectError> list = result.getAllErrors();
//            return String.ok(list);
//        }
//        return String.ok(billService.update(billRequest, id));
//
//    }
}
