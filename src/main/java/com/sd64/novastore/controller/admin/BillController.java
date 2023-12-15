package com.sd64.novastore.controller.admin;

import com.sd64.novastore.dto.admin.BillDto;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.model.PaymentMethod;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/nova/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping()
    public String getAllBills(Model model,
                              @RequestParam(defaultValue = "0", value = "page") Integer page,
                              @RequestParam(name = "sort", defaultValue = "createDate,desc") String sortField,
                              @RequestParam(name = "code", required = false) String code,
                              @RequestParam(name = "ngayTaoStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoStart,
                              @RequestParam(name  = "ngayTaoEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoEnd,
                              @RequestParam(name = "status", required = false) Integer status,
                              @RequestParam(name = "type", required = false) Integer type,
                              @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                              @RequestParam(name = "customerName", required = false) String customerName) {
        int pageSize = 8;
        String[] sortParams = sortField.split(",");
        String sortFieldName = sortParams[0];
        Sort.Direction sortDirection = Sort.Direction.ASC;

        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }

        Sort sort = Sort.by(sortDirection, sortFieldName);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Page<BillDto> listBill;
        if (ngayTaoStart != null || ngayTaoEnd != null || code != null || status != null || type != null || customerName != null || phoneNumber != null) {
            if(ngayTaoStart != null) {
                model.addAttribute("ngayTaoStart", formatter.format(ngayTaoStart));
            }
            if(ngayTaoEnd != null) {
                model.addAttribute("ngayTaoEnd", formatter.format(ngayTaoEnd));
            }
            listBill = billService.searchListBill(code.trim(), ngayTaoStart, ngayTaoEnd, status, type, phoneNumber.trim(), customerName.trim(), pageable);
        } else {
            listBill = billService.findAll(pageable);
        }

        model.addAttribute("sortField", sortFieldName);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("items", listBill);

        model.addAttribute("code", code);
        model.addAttribute("status", status);
        model.addAttribute("type", type);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("customerName", customerName);
        model.addAttribute("sortField", sortField);
        return "admin/bill/bill";
    }

    @GetMapping("/detail/{id}")
    public String getBillDetail(Model model, @PathVariable("id") Integer id) {
        Bill bill = billService.getOneBill(id);
        List<BillDetail> lstBillDetails = billService.getLstDetailByBillId(id);
        List<PaymentMethod> listPaymentMethod = paymentMethodService.getAllBillPaymentMethod(id);
        model.addAttribute("bill", bill);
        model.addAttribute("lstBillDetails", lstBillDetails);
        model.addAttribute("listPaymentMethod", listPaymentMethod);
        return "admin/bill/bill-detail";
    }

//    @GetMapping("/detail/{id}")
//    public String detail(@PathVariable Integer id,
//                         Model model) {
//        List<BillDetail> lstBillDetails = billService.getLstDetailByBillId(id);
//        model.addAttribute("page", lstBillDetails);
//        return "/admin/bill/bill-detail";
//    }

    @RequestMapping(value = "/cancel-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelBill(@PathVariable Integer id, RedirectAttributes attributes) {
        billService.cancelOrder(id);
        return "redirect:/nova/bill/page";
    }

    @RequestMapping(value = "/confirm-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String confirmBill(@PathVariable Integer id, RedirectAttributes attributes) {
        billService.acceptBill(id);
        return "redirect:/nova/bill/page";
    }

    @PostMapping("/shipping-bill")
    public String shippingBill(@RequestParam("id") Integer id,
                              @RequestParam("shippingFee") BigDecimal shippingFee){
        billService.shippingOrder(id, shippingFee);
        return "redirect:/nova/bill/page";
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
