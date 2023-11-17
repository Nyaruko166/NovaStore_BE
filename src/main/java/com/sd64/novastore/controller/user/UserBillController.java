package com.sd64.novastore.controller.user;

import com.sd64.novastore.model.Address;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.service.AddressService;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.CustomerService;
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

    @GetMapping("/checkout")
    public String checkOut(Principal principal, Model model){
        if (principal == null){
            return "redirect:/login";
        }
        Customer customer = customerService.findByEmail(principal.getName());
        Cart cart = customer.getCart();
        Address defaultAddress = addressService.findAccountDefaultAddress(customer.getId());
        List<Address> listAddress = addressService.findAccountAddress(customer.getId());
        model.addAttribute("cart", cart);
        model.addAttribute("defaultAddress", defaultAddress);
        model.addAttribute("listAddress", listAddress);
        return "/user/checkout";
    }

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        Customer customer = customerService.findByEmail(principal.getName());
        List<Bill> listBill = billService.getAllOrders(customer.getId());
        model.addAttribute("orders", listBill);
        return "/user/order";
    }

    @PostMapping("/add-order")
    public String createOrder(Principal principal,
                              Model model,
                              HttpSession session,
                              @RequestParam("address") String address,
                              @RequestParam("payment") String payment){
        if (principal == null){
            return "redirect:/login";
        }
        Customer customer = customerService.findByEmail(principal.getName());
        Cart cart = customer.getCart();
        Bill bill = billService.placeOrder(cart, address, payment);
        session.removeAttribute("totalItems");
        return "redirect:/home";
    }

    @RequestMapping(value = "/cancel-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(@PathVariable Integer id, RedirectAttributes attributes) {
        billService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Huỷ đơn hàng thành công!");
        return "redirect:/orders";
    }
}
