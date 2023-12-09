package com.sd64.novastore.controller.user;

import com.google.gson.JsonObject;
import com.sd64.novastore.model.Address;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.response.MomoPaymentResponse;
import com.sd64.novastore.response.VNPaymentResponse;
import com.sd64.novastore.service.AddressService;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.CustomerService;
import com.sd64.novastore.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@Controller
public class UserBillController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private BillService billService;

    @Autowired
    private PaymentService paymentService;

    JsonObject jsonData = new JsonObject();

    @GetMapping("/checkout")
    public String checkOut(Principal principal, Model model) {
//        if (principal == null) {
//            return "redirect:/login";
//        }
        Customer customer = customerService.findByEmail(principal.getName());
        Cart cart = customer.getCart();
        Address defaultAddress = addressService.findAccountDefaultAddress(customer.getId());
        List<Address> listAddress = addressService.findAccountAddress(customer.getId());
        if (defaultAddress == null && listAddress.isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("defaultAddress", defaultAddress);
        model.addAttribute("listAddress", listAddress);
        return "/user/checkout2";
    }

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByEmail(principal.getName());
        List<Bill> listBill = billService.getAllOrders(customer.getId());
        model.addAttribute("orders", listBill);
        return "/user/order";
    }

    @GetMapping("/order-detail/{id}")
    public String getOrderDetail(@PathVariable Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Bill bill = billService.getOneBill(id);
        model.addAttribute("order", bill);
        return "/user/order-detail";
    }

    @PostMapping("/add-order")
    public String createOrder(Principal principal,
                              RedirectAttributes attributes,
                              HttpSession session,
                              @RequestParam("address") String address,
                              @RequestParam("payment") String payment,
                              HttpServletRequest request) throws IOException, URISyntaxException {
        if (principal == null){
            return "redirect:/login";
        }
        Customer customer = customerService.findByEmail(principal.getName());
        Cart cart = customer.getCart();
        if (payment.equals("VNPAY")){
            //Long dok
            jsonData = paymentService.vnpayCreate(request, cart.getTotalPrice().longValue(), address);
            return "redirect:" + jsonData.get("payUrl").toString().replaceAll("\"", "");
        }
        if (payment.equals("Momo")){
            //Long dok
            jsonData = paymentService.MomoPayCreate(cart.getTotalPrice().longValue(), address);
            return "redirect:" + jsonData.get("payUrl").toString().replaceAll("\"", "");
        }
        billService.placeOrder(cart, address, payment);
        session.removeAttribute("totalItems");
        attributes.addFlashAttribute("success", "Đặt hàng thành công!");
        return "redirect:/orders";
    }

    @RequestMapping(value = "/cancel-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(@PathVariable Integer id, RedirectAttributes attributes) {
        billService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Huỷ đơn hàng thành công!");
        return "redirect:/orders";
    }

    @GetMapping("/vnpay/return")
    public String vnpayReturn(Principal principal, HttpSession session, VNPaymentResponse VNPaymentResponse, RedirectAttributes attributes) {
        if (VNPaymentResponse.getVnp_ResponseCode().equals("00")) {
            Customer customer = customerService.findByEmail(principal.getName());
            Cart cart = customer.getCart();
            String payment = "Thanh toán qua VNPAY";
            String address = jsonData.get("address").toString().replaceAll("\"", "");
            billService.placeOrder(cart, address, payment);
            session.removeAttribute("totalItems");
            attributes.addFlashAttribute("success", "Đặt hàng thành công!");
            return "redirect:/orders";
        } else {
            attributes.addFlashAttribute("mess", "Thanh toán không thành công");
            return "redirect:/checkout";
        }
    }

    @GetMapping("/momo/return")
    public String momoReturn(Principal principal, HttpSession session,MomoPaymentResponse momoPaymentResponse, RedirectAttributes attributes) {
        if (momoPaymentResponse.getResultCode().equals("0")) {
            Customer customer = customerService.findByEmail(principal.getName());
            Cart cart = customer.getCart();
            String payment = "Thanh toán qua Momo";
            String address = jsonData.get("address").toString().replaceAll("\"", "");
            billService.placeOrder(cart, address, payment);
            session.removeAttribute("totalItems");
            attributes.addFlashAttribute("success", "Đặt hàng thành công!");
            return "redirect:/orders";
        } else {
            attributes.addFlashAttribute("mess", "Thanh toán không thành công");
            return "redirect:/checkout";
        }
    }
}
