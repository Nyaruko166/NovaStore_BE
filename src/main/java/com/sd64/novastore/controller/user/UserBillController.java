package com.sd64.novastore.controller.user;

import com.google.gson.JsonObject;
import com.sd64.novastore.model.Address;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.SessionCart;
import com.sd64.novastore.model.Voucher;
import com.sd64.novastore.response.MomoPaymentResponse;
import com.sd64.novastore.response.VNPaymentResponse;
import com.sd64.novastore.service.AddressService;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.CartService;
import com.sd64.novastore.service.CustomerService;
import com.sd64.novastore.service.PaymentService;
import com.sd64.novastore.service.VoucherService;
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
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@Controller
public class UserBillController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private BillService billService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private VoucherService voucherService;

    JsonObject jsonData = new JsonObject();

    @GetMapping("/checkout")
    public String checkOut(Principal principal, Model model, HttpSession session) {
        if (principal == null) {
            SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
            if (sessionCart.getCartDetails().isEmpty()){
                return "redirect:/cart";
            }
            if (!sessionCart.getCartDetails().isEmpty()){
                cartService.reloadCartDetailSession(sessionCart);
                session.setAttribute("sessionCart", sessionCart);
                session.setAttribute("totalItems", sessionCart.getTotalItems());
            }
            List<Voucher> listVoucher = voucherService.getVoucherByCartPrice(sessionCart.getTotalPrice());
            model.addAttribute("cart", sessionCart);
            model.addAttribute("listVoucher", listVoucher);
            model.addAttribute("loggedIn", false);
            model.addAttribute("email", "");
        } else {
            Customer customer = customerService.findByEmail(principal.getName());
            Cart cart = cartService.getCart(principal.getName());
            if (cart.getCartDetails().isEmpty()){
                return "redirect:/cart";
            }
            if (!cart.getCartDetails().isEmpty()){
                cartService.reloadCartDetail(cart);
                session.setAttribute("totalItems", cart.getTotalItems());
            }
            Address defaultAddress = addressService.findAccountDefaultAddress(customer.getId());
            List<Address> listAddress = addressService.findAccountAddress(customer.getId());
            List<Voucher> listVoucher = voucherService.getVoucherByCartPrice(cart.getTotalPrice());
            model.addAttribute("cart", cart);
            model.addAttribute("listVoucher", listVoucher);
            model.addAttribute("loggedIn", true);
            model.addAttribute("email", principal.getName());
            model.addAttribute("defaultAddress", defaultAddress);
            model.addAttribute("listAddress", listAddress);
        }
        return "/user/checkout";
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
                              @RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("phoneNumber") String phoneNumber,
                              @RequestParam("specificAddress") String specificAddress,
                              @RequestParam("city") String city,
                              @RequestParam("district") String district,
                              @RequestParam("ward") String ward,
                              @RequestParam("payment") String payment,
                              @RequestParam(value = "voucher", required = false) Integer voucher,
                              HttpServletRequest request) throws IOException, URISyntaxException {
        String address = specificAddress + ", " + ward + ", " + district + ", " + city;
        if (principal == null){
            SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
            if (payment.equals("VNPAY")){
                //Long dok
                if (voucher == null){
                    jsonData = paymentService.vnpayCreate(request, sessionCart.getTotalPrice().longValue(), address, name, phoneNumber, email, String.valueOf(voucher));
                } else {
                    Voucher uuDai = voucherService.getVoucherById(voucher);
                    BigDecimal totalPrice = sessionCart.getTotalPrice().subtract(uuDai.getValue());
                    jsonData = paymentService.vnpayCreate(request, totalPrice.longValue(), address, name, phoneNumber, email, String.valueOf(voucher));
                }
                return "redirect:" + jsonData.get("payUrl").toString().replaceAll("\"", "");
            }
            if (payment.equals("Momo")){
                //Long dok
                if (voucher == null){
                    jsonData = paymentService.vnpayCreate(request, sessionCart.getTotalPrice().longValue(), address, name, phoneNumber, email, String.valueOf(voucher));
                } else {
                    Voucher uuDai = voucherService.getVoucherById(voucher);
                    BigDecimal totalPrice = sessionCart.getTotalPrice().subtract(uuDai.getValue());
                    jsonData = paymentService.vnpayCreate(request, totalPrice.longValue(), address, name, phoneNumber, email, String.valueOf(voucher));
                }
                return "redirect:" + jsonData.get("payUrl").toString().replaceAll("\"", "");
            }
            billService.placeOrderSession(sessionCart, email, name, address, phoneNumber, payment, voucher);
            session.removeAttribute("totalItems");
            return "redirect:/home";
        } else {
            Cart cart = cartService.getCart(principal.getName());
            if (payment.equals("VNPAY")){
                //Long dok
                if (voucher == null){
                    jsonData = paymentService.vnpayCreate(request, cart.getTotalPrice().longValue(), address, name, phoneNumber, email, String.valueOf(voucher));
                } else {
                    Voucher uuDai = voucherService.getVoucherById(voucher);
                    BigDecimal totalPrice = cart.getTotalPrice().subtract(uuDai.getValue());
                    jsonData = paymentService.vnpayCreate(request, totalPrice.longValue(), address, name, phoneNumber, email, String.valueOf(voucher));
                }
                return "redirect:" + jsonData.get("payUrl").toString().replaceAll("\"", "");
            }
            if (payment.equals("Momo")){
                //Long dok
                if (voucher == null){
                    jsonData = paymentService.vnpayCreate(request, cart.getTotalPrice().longValue(), address, name, phoneNumber, email, String.valueOf(voucher));
                } else {
                    Voucher uuDai = voucherService.getVoucherById(voucher);
                    BigDecimal totalPrice = cart.getTotalPrice().subtract(uuDai.getValue());
                    jsonData = paymentService.vnpayCreate(request, totalPrice.longValue(), address, name, phoneNumber, email, String.valueOf(voucher));
                }
                return "redirect:" + jsonData.get("payUrl").toString().replaceAll("\"", "");
            }
            billService.placeOrder(cart, name, address, phoneNumber, payment, voucher);
            session.removeAttribute("totalItems");
            attributes.addFlashAttribute("success", "Đặt hàng thành công!");
        }
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
        if (principal == null) {
            if (VNPaymentResponse.getVnp_ResponseCode().equals("00")) {
                SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
                String payment = "Thanh toán qua VNPAY";
                String address = jsonData.get("address").toString().replaceAll("\"", "");
                String name = jsonData.get("name").toString().replaceAll("\"", "");
                String phoneNumber = jsonData.get("phoneNumber").toString().replaceAll("\"", "");
                String email = jsonData.get("email").toString().replaceAll("\"", "");
                String voucher = jsonData.get("voucher").toString().replaceAll("\"", "");
                billService.placeOrderSession(sessionCart, email, name, address, phoneNumber, payment, Integer.valueOf(voucher));
                session.removeAttribute("totalItems");
                return "redirect:/home";
            } else {
                attributes.addFlashAttribute("mess", "Thanh toán không thành công");
                return "redirect:/checkout";
            }
        } else {
            if (VNPaymentResponse.getVnp_ResponseCode().equals("00")) {
                Cart cart = cartService.getCart(principal.getName());
                String payment = "Thanh toán qua VNPAY";
                String address = jsonData.get("address").toString().replaceAll("\"", "");
                String name = jsonData.get("name").toString().replaceAll("\"", "");
                String phoneNumber = jsonData.get("phoneNumber").toString().replaceAll("\"", "");
                String voucher = jsonData.get("voucher").toString().replaceAll("\"", "");
                billService.placeOrder(cart, name, address, phoneNumber, payment, Integer.valueOf(voucher));
                session.removeAttribute("totalItems");
                attributes.addFlashAttribute("success", "Đặt hàng thành công!");
                return "redirect:/orders";
            } else {
                attributes.addFlashAttribute("mess", "Thanh toán không thành công");
                return "redirect:/checkout";
            }
        }
    }

    @GetMapping("/momo/return")
    public String momoReturn(Principal principal, HttpSession session,MomoPaymentResponse momoPaymentResponse, RedirectAttributes attributes) {
        if (principal == null){
            if (momoPaymentResponse.getResultCode().equals("0")) {
                SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
                String payment = "Thanh toán qua Momo";
                String address = jsonData.get("address").toString().replaceAll("\"", "");
                String name = jsonData.get("name").toString().replaceAll("\"", "");
                String phoneNumber = jsonData.get("phoneNumber").toString().replaceAll("\"", "");
                String email = jsonData.get("email").toString().replaceAll("\"", "");
                String voucher = jsonData.get("voucher").toString().replaceAll("\"", "");
                billService.placeOrderSession(sessionCart, email, name, address, phoneNumber, payment, Integer.valueOf(voucher));
                session.removeAttribute("totalItems");
                return "redirect:/home";
            } else {
                attributes.addFlashAttribute("mess", "Thanh toán không thành công");
                return "redirect:/checkout";
            }
        } else {
            if (momoPaymentResponse.getResultCode().equals("0")) {
                Cart cart = cartService.getCart(principal.getName());
                String payment = "Thanh toán qua Momo";
                String address = jsonData.get("address").toString().replaceAll("\"", "");
                String name = jsonData.get("name").toString().replaceAll("\"", "");
                String phoneNumber = jsonData.get("phoneNumber").toString().replaceAll("\"", "");
                String voucher = jsonData.get("voucher").toString().replaceAll("\"", "");
                billService.placeOrder(cart, name, address, phoneNumber, payment, Integer.valueOf(voucher));
                session.removeAttribute("totalItems");
                attributes.addFlashAttribute("success", "Đặt hàng thành công!");
                return "redirect:/orders";
            } else {
                attributes.addFlashAttribute("mess", "Thanh toán không thành công");
                return "redirect:/checkout";
            }
        }
    }
}
