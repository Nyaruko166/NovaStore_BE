package com.sd64.novastore.controller.admin;

import com.sd64.novastore.dto.admin.BillDto;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.PaymentMethod;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.repository.AccountRepository;
import com.sd64.novastore.repository.BillDetailRepository;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.PaymentMethodService;
import com.sd64.novastore.service.ProductDetailService;
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
import java.security.Principal;
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

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

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
        List<ProductDetail> listProductDetail = productDetailService.findAllActive();
        Bill bill = billService.getOneBill(id);
        if (bill != null){
            List<BillDetail> lstBillDetails = billService.getLstDetailByBillId(id);
            List<PaymentMethod> listPaymentMethod = paymentMethodService.getAllBillPaymentMethod(id);
            model.addAttribute("bill", bill);
            model.addAttribute("lstBillDetails", lstBillDetails);
            model.addAttribute("listProductDetail", listProductDetail);
            model.addAttribute("listPaymentMethod", listPaymentMethod);
            return "admin/bill/bill-detail";
        } else {
            attributes.addFlashAttribute("error", "Không tìm thấy hoá đơn");
            return "redirect:/nova/bill";
        }
    }

    @RequestMapping(value = "/cancel-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelBill(@PathVariable Integer id, RedirectAttributes attributes, Principal principal) {
        Bill bill = billService.getOneBill(id);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.cancelOrder(id, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Huỷ đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("error", "Đơn hàng " + bill.getCode() + " đã ở trạng thái huỷ từ trước");
            }
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @RequestMapping(value = "/detail-cancel-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String detailCancelBill(@PathVariable Integer id, RedirectAttributes attributes, Principal principal) {
        Bill bill = billService.getOneBill(id);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.cancelOrder(id, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Huỷ đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("error", "Đơn hàng " + bill.getCode() + " đã ở trạng thái huỷ từ trước");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
            return "redirect:/nova/bill";
        }
    }

    @PostMapping("/change-bill-address")
    public String confirmBill(@RequestParam("id") Integer id,
                              @RequestParam("customerName") String customerName,
                              @RequestParam("phoneNumber") String phoneNumber,
                              @RequestParam("specificAddress") String specificAddress,
                              @RequestParam("city") String city,
                              @RequestParam("district") String district,
                              @RequestParam("ward") String ward,
                              RedirectAttributes attributes, Principal principal){
        Bill bill = billService.getOneBill(id);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.changeAddressOrder(id, customerName.trim(), phoneNumber.trim(), specificAddress.trim(), ward, district, city, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Đổi địa chỉ nhận hàng của đơn " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("error", "Chỉ đổi được địa chỉ cho đơn hàng có trạng thái chờ xác nhận");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
            return "redirect:/nova/bill";
        }
    }

    @PostMapping("/add-bill-item")
    public String addBillItem(@RequestParam("billId") Integer billId,
                              @RequestParam("productDetailId") Integer productDetailId,
                              @RequestParam("productDetailQuantity") Integer quantity,
                              RedirectAttributes attributes, Principal principal){
        Bill bill = billService.getOneBill(billId);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.addBillItem(billId, productDetailId, quantity, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Thêm sản phẩm cho đơn hàng thành công");
            } else {
                attributes.addFlashAttribute("error", "Không thể thêm vì số lượng vượt quá số lượng tồn của sản phẩm");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
            return "redirect:/nova/bill";
        }
    }

    @RequestMapping(value = "/update-bill-item", method = RequestMethod.POST, params = "action=update")
    public String updateBillItem(@RequestParam("id") Integer id,
                                 @RequestParam("quantity") Integer quantity,
                                 RedirectAttributes attributes, Principal principal){
        BillDetail billDetail = billDetailRepository.findById(id).orElse(null);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        boolean check = billService.updateBillItem(id, quantity, employee);
        if (check){
            attributes.addFlashAttribute("mess", "Sửa thành công");
        } else {
            attributes.addFlashAttribute("error", "Sửa thất bại");
        }
        return "redirect:/nova/bill/detail/" + billDetail.getBill().getId();
    }

    @RequestMapping(value = "/update-bill-item", method = RequestMethod.POST, params = "action=delete")
    public String deleteBillItem(@RequestParam("id") Integer id,
                                 RedirectAttributes attributes, Principal principal){
        BillDetail billDetail = billDetailRepository.findById(id).orElse(null);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        boolean check = billService.deleteBillItem(id, employee);
        if (check){
            attributes.addFlashAttribute("mess", "Xoá thành công");
        } else {
            attributes.addFlashAttribute("error", "Không thể xoá do hoá đơn cần có ít nhất 1 sản phẩm");
        }
        return "redirect:/nova/bill/detail/" + billDetail.getBill().getId();
    }

    @RequestMapping(value = "/confirm-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String confirmBill(@PathVariable("id") Integer id,
                              RedirectAttributes attributes, Principal principal){
        Bill bill = billService.getOneBill(id);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.confirmOrder(id, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Xác nhận đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("error", "Không thể xác nhận đơn có trạng thái khác chờ xác nhận");
            }
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @RequestMapping(value = "/detail-confirm-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String detailConfirmBill(@PathVariable("id") Integer id,
                              RedirectAttributes attributes, Principal principal){
        Bill bill = billService.getOneBill(id);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.confirmOrder(id, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Xác nhận đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("error", "Không thể xác nhận đơn có trạng thái khác chờ xác nhận");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
            return "redirect:/nova/bill";
        }
    }

    @RequestMapping(value = "/shipping-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String shippingBill(@PathVariable Integer id, RedirectAttributes attributes, Principal principal) {
        Bill bill = billService.getOneBill(id);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.shippingOrder(id, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Đã xác nhận đang giao đơn hàng " + bill.getCode());
            } else {
                attributes.addFlashAttribute("error", "Không thể xác nhận đang giao cho đơn có trạng thái khác chờ giao hàng");
            }
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @RequestMapping(value = "/detail-shipping-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String detailShippingBill(@PathVariable Integer id, RedirectAttributes attributes, Principal principal) {
        Bill bill = billService.getOneBill(id);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.shippingOrder(id, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Đã xác nhận đang giao đơn hàng " + bill.getCode());
            } else {
                attributes.addFlashAttribute("error", "Không thể xác nhận đang giao cho đơn có trạng thái khác chờ giao hàng");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @RequestMapping(value = "/complete-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String completeBill(@PathVariable Integer id, RedirectAttributes attributes, Principal principal) {
        Bill bill = billService.getOneBill(id);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.completeOrder(id, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Xác nhận đã giao đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("error", "Không thể xác nhận đã giao cho đơn có trạng thái khác đang giao hàng");
            }
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
        }
        return "redirect:/nova/bill";
    }

    @RequestMapping(value = "/detail-complete-bill/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String detailCompleteBill(@PathVariable Integer id, RedirectAttributes attributes, Principal principal) {
        Bill bill = billService.getOneBill(id);
        String email = principal.getName();
        Account employee = accountRepository.findFirstByEmail(email);
        if (bill != null){
            boolean check = billService.completeOrder(id, employee);
            if (check){
                attributes.addFlashAttribute("mess", "Xác nhận đã giao đơn hàng " + bill.getCode() + " thành công");
            } else {
                attributes.addFlashAttribute("error", "Không thể xác nhận đã giao cho đơn có trạng thái khác đang giao hàng");
            }
            return "redirect:/nova/bill/detail/" + bill.getId();
        } else {
            attributes.addFlashAttribute("error", "Không có thông tin đơn hàng này");
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
