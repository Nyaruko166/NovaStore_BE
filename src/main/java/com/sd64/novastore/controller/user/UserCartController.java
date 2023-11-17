package com.sd64.novastore.controller.user;

import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.service.CartService;
import com.sd64.novastore.service.CustomerService;
import com.sd64.novastore.service.user.UserProductDetailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserCartController {
    @Autowired
    private UserProductDetailService userProductDetailService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        if (principal == null){
            return "redirect:/login";
        }
        Customer customer = customerService.findByEmail(principal.getName());
        Cart cart = customer.getCart();
        if (cart == null){
            model.addAttribute("check", "Giỏ hàng của bạn đang trống");
        }
        if (cart != null){
            model.addAttribute("grandTotal", cart.getTotalPrice());
            model.addAttribute("cart", cart);
            session.setAttribute("totalItems", cart.getTotalItems());
        }
        if (cart.getCartDetails().isEmpty()){
            model.addAttribute("check", "Giỏ hàng của bạn đang trống");
        }
        return "/user/cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("color") Integer colorId,
                            @RequestParam("size") Integer sizeId,
                            @RequestParam("quantity") Integer quantity,
                            @RequestParam("productId") Integer productId,
                            Principal principal,
                            RedirectAttributes redirectAttributes,
                            HttpSession session){
        ProductDetail productDetail = userProductDetailService.getProductDetail(productId, sizeId, colorId);
        if (principal == null){
            return "redirect:/login";
        }
        String email = principal.getName();
        Cart cart = cartService.addToCart(productDetail, quantity, email);
        session.setAttribute("totalItems", cart.getTotalItems());
        redirectAttributes.addFlashAttribute("mess", "Thêm giỏ hàng thành công!");
        String redirectUrl = String.format("redirect:/product-detail/%d", productId);
        return redirectUrl;
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Integer id,
                             @RequestParam("quantity") Integer quantity,
                             Principal principal,
                             HttpSession session){
        if (principal == null){
            return "redirect:/login";
        }
        ProductDetail productDetail = userProductDetailService.getProductDetailById(id);
        String email = principal.getName();
        Cart cart = cartService.updateCart(productDetail, quantity, email);
        session.setAttribute("totalItems", cart.getTotalItems());
        return "redirect:/cart";
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Integer id,
                             Principal principal,
                             HttpSession session){
        if (principal == null){
            return "redirect:/login";
        }
        ProductDetail productDetail = userProductDetailService.getProductDetailById(id);
        String email = principal.getName();
        Cart cart = cartService.removeFromCart(productDetail, email);
        session.setAttribute("totalItems", cart.getTotalItems());
        return "redirect:/cart";
    }
}