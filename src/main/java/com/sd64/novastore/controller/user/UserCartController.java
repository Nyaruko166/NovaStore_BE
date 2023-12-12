package com.sd64.novastore.controller.user;

import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.SessionCart;
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
            SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
            if (sessionCart == null){
                model.addAttribute("check", "Giỏ hàng của bạn đang trống");
            }
            if (sessionCart != null){
                model.addAttribute("grandTotal", sessionCart.getTotalPrice());
                model.addAttribute("cart", sessionCart);
                session.setAttribute("totalItems", sessionCart.getTotalItems());
                if (sessionCart.getCartDetails().isEmpty()){
                    model.addAttribute("check", "Giỏ hàng của bạn đang trống");
                }
            }
        } else {
            Customer customer = customerService.findByEmail(principal.getName());
            Cart cart = customer.getCart();
            if (cart == null){
                model.addAttribute("check", "Giỏ hàng của bạn đang trống");
            }
            if (cart != null){
                model.addAttribute("grandTotal", cart.getTotalPrice());
                model.addAttribute("cart", cart);
                session.setAttribute("totalItems", cart.getTotalItems());
                if (cart.getCartDetails().isEmpty()){
                    model.addAttribute("check", "Giỏ hàng của bạn đang trống");
                }
            }
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
            SessionCart oldSessionCart = (SessionCart) session.getAttribute("sessionCart");
            SessionCart sessionCart = cartService.addToCartSession(oldSessionCart, productDetail, quantity);
            session.setAttribute("sessionCart", sessionCart);
            session.setAttribute("totalItems", sessionCart.getTotalItems());
            redirectAttributes.addFlashAttribute("mess", "Thêm giỏ hàng thành công!");
        } else {
            String email = principal.getName();
            Cart cart = cartService.addToCart(productDetail, quantity, email);
            session.setAttribute("totalItems", cart.getTotalItems());
            redirectAttributes.addFlashAttribute("mess", "Thêm giỏ hàng thành công!");
        }
        String redirectUrl = String.format("redirect:/product-detail/%d", productId);
        return redirectUrl;
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Integer id,
                             @RequestParam("quantity") Integer quantity,
                             Principal principal,
                             RedirectAttributes redirectAttributes,
                             HttpSession session){
        ProductDetail productDetail = userProductDetailService.getProductDetailById(id);
        if (principal == null){
            SessionCart oldSessionCart = (SessionCart) session.getAttribute("sessionCart");
            boolean check = cartService.updateCartSession(oldSessionCart, productDetail, quantity);
            if (check){
                SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
                session.setAttribute("sessionCart", sessionCart);
                session.setAttribute("totalItems", sessionCart.getTotalItems());
                redirectAttributes.addFlashAttribute("success","Đã update số lượng sản phẩm");
            } else {
                redirectAttributes.addFlashAttribute("success","Số lượng phải nhỏ hơn hoặc bằng số lượng tồn");
            }
        } else {
            String email = principal.getName();
            boolean check = cartService.updateCart(productDetail, quantity, email);
            if (check){
                redirectAttributes.addFlashAttribute("success","Đã update số lượng sản phẩm");
                Cart cart = cartService.getCart(email);
                session.setAttribute("totalItems", cart.getTotalItems());
            } else {
                redirectAttributes.addFlashAttribute("success","Số lượng phải nhỏ hơn hoặc bằng số lượng tồn");
            }
        }
        return "redirect:/cart";
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Integer id,
                             Principal principal,
                             HttpSession session){
        ProductDetail productDetail = userProductDetailService.getProductDetailById(id);
        if (principal == null){
            SessionCart oldSessionCart = (SessionCart) session.getAttribute("sessionCart");
            SessionCart sessionCart = cartService.removeFromCartSession(oldSessionCart, productDetail);
            session.setAttribute("sessionCart", sessionCart);
            session.setAttribute("totalItems", sessionCart.getTotalItems());
        } else {
            String email = principal.getName();
            Cart cart = cartService.removeFromCart(productDetail, email);
            session.setAttribute("totalItems", cart.getTotalItems());
        }
        return "redirect:/cart";
    }
}
