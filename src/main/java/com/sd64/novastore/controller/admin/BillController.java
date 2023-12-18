package com.sd64.novastore.controller.admin;

import com.sd64.novastore.dto.admin.BillDto;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.PaymentMethod;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.PaymentMethodService;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
            listBill = billService.searchListBill(code.trim(), ngayTaoStart, ngayTaoEnd, status, type, phoneNumber.trim(), customerName.trim(), pageable);
            if(ngayTaoEnd != null) {
                model.addAttribute("ngayTaoEnd", formatter.format(ngayTaoEnd));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ngayTaoEnd);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                Date ngayEnd = calendar.getTime();
                listBill = billService.searchListBill(code.trim(), ngayTaoStart, ngayEnd, status, type, phoneNumber.trim(), customerName.trim(), pageable);
            }
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
    public String getBillDetail(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            List<BillDetail> lstBillDetails = billService.getLstDetailByBillId(id);
            List<PaymentMethod> listPaymentMethod = paymentMethodService.getAllBillPaymentMethod(id);
            model.addAttribute("bill", bill);
            model.addAttribute("lstBillDetails", lstBillDetails);
            model.addAttribute("listPaymentMethod", listPaymentMethod);
            return "admin/bill/bill-detail";
        } else {
            attributes.addFlashAttribute("message", "Không tìm thấy hoá đơn");
            return "redirect:/nova/bill";
        }
    }

    @RequestMapping(value = "/cancel-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelBill(@PathVariable Integer id, RedirectAttributes attributes) {
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            boolean check = billService.cancelOrder(id);
            if (check){
                attributes.addFlashAttribute("message", "Huỷ đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("message", "Đơn hàng " + bill.getCode() + " đã ở trạng thái huỷ từ trước");
            }
        } else {
            attributes.addFlashAttribute("message", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @RequestMapping(value = "/detail-cancel-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String detailCancelBill(@PathVariable Integer id, RedirectAttributes attributes) {
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            boolean check = billService.cancelOrder(id);
            if (check){
                attributes.addFlashAttribute("message", "Huỷ đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("message", "Đơn hàng " + bill.getCode() + " đã ở trạng thái huỷ từ trước");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("message", "Không có thông tin đơn hàng này");
            return "redirect:/nova/bill";
        }
    }

    @PostMapping("/confirm-bill")
    public String confirmBill(@RequestParam("id") Integer id,
                               @RequestParam("shippingFee") BigDecimal shippingFee,
                              RedirectAttributes attributes){
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            boolean check = billService.confirmOrder(shippingFee, id);
            if (check){
                attributes.addFlashAttribute("message", "Xác nhận đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("message", "Không thể xác nhận đơn có trạng thái khác chờ xác nhận");
            }
        } else {
            attributes.addFlashAttribute("message", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @PostMapping("/detail-confirm-bill")
    public String detailConfirmBill(@RequestParam("id") Integer id,
                              @RequestParam("shippingFee") BigDecimal shippingFee,
                              RedirectAttributes attributes){
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            boolean check = billService.confirmOrder(shippingFee, id);
            if (check){
                attributes.addFlashAttribute("message", "Xác nhận đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("message", "Không thể xác nhận đơn có trạng thái khác chờ xác nhận");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("message", "Không có thông tin đơn hàng này");
            return "redirect:/nova/bill";
        }
    }

    @RequestMapping(value = "/shipping-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String shippingBill(@PathVariable Integer id, RedirectAttributes attributes) {
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            boolean check = billService.shippingOrder(id);
            if (check){
                attributes.addFlashAttribute("message", "Đã xác nhận đang giao đơn hàng " + bill.getCode());
            } else {
                attributes.addFlashAttribute("message", "Không thể xác nhận đang giao cho đơn có trạng thái khác chờ giao hàng");
            }
        } else {
            attributes.addFlashAttribute("message", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @RequestMapping(value = "/detail-shipping-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String detailShippingBill(@PathVariable Integer id, RedirectAttributes attributes) {
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            boolean check = billService.shippingOrder(id);
            if (check){
                attributes.addFlashAttribute("message", "Đã xác nhận đang giao đơn hàng " + bill.getCode());
            } else {
                attributes.addFlashAttribute("message", "Không thể xác nhận đang giao cho đơn có trạng thái khác chờ giao hàng");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("message", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @RequestMapping(value = "/complete-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String completeBill(@PathVariable Integer id, RedirectAttributes attributes) {
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            boolean check = billService.completeOrder(id);
            if (check){
                attributes.addFlashAttribute("message", "Xác nhận đã giao đơn hàng " + bill.getCode() + " thành công");
            } else {
            }
        } else {
            attributes.addFlashAttribute("message", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @RequestMapping(value = "/detail-complete-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String detailCompleteBill(@PathVariable Integer id, RedirectAttributes attributes) {
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            boolean check = billService.completeOrder(id);
            if (check){
                attributes.addFlashAttribute("message", "Xác nhận đã giao đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("message", "Không thể xác nhận đã giao cho đơn có trạng thái khác đang giao hàng");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("message", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @GetMapping("/export-bill")
    public void exportBill(
            HttpServletResponse response,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sort", defaultValue = "id,asc") String sortField,
            @RequestParam(name = "ngayTaoStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoStart,
            @RequestParam(name = "ngayTaoEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoEnd,
            UriComponentsBuilder uriBuilder
    ) throws IOException {
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

        if (ngayTaoStart != null) {
            listBill = billService.searchListBill(null, ngayTaoStart, ngayTaoEnd, null, null, null, null, pageable);
        } else if (ngayTaoEnd != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(ngayTaoEnd);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            Date ngayEnd = calendar.getTime();
            listBill = billService.searchListBill(null, null, ngayEnd, null, null, null, null, pageable);
        } else {
            listBill = billService.findAll(pageable);
        }

        String exportUrl = uriBuilder.path("/export-bill")
                .queryParam("page", page)
                .queryParam("sort", sortField)
                .queryParam("ngayTaoStart", ngayTaoStart)
                .queryParam("ngayTaoEnd", ngayTaoEnd)
                .toUriString();

        billService.exportToExcel(response, listBill, exportUrl);
    }
}
