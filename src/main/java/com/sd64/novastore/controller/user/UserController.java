package com.sd64.novastore.controller.user;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.model.*;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.SessionCart;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.service.CartService;
import com.sd64.novastore.service.CustomerService;
import com.sd64.novastore.service.ImageService;
import com.sd64.novastore.service.user.ProductViewService;
import com.sd64.novastore.service.user.UserColorService;
import com.sd64.novastore.service.user.UserSizeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private ProductViewService productViewService;

    @Autowired
    private UserSizeService userSizeService;

    @Autowired
    private UserColorService userColorService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ImageService imageService;
    private CartService cartService;

    @GetMapping("/home")
    public String home(Model model, Principal principal, HttpSession session){
        if (principal != null){
            Customer customer = customerService.findByEmail(principal.getName());
            session.setAttribute("username", customer.getName());
            Cart cart = customer.getCart();
            SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
            if (sessionCart != null){
                cartService.combineCart(sessionCart, principal.getName());
                session.removeAttribute("sessionCart");
            }
            if (cart != null){
                session.setAttribute("totalItems", cart.getTotalItems());
            }
        }
        List<Product> productViews = productViewService.getAllProductResponse();
        model.addAttribute("productViews", productViews);
        return "/user/home";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        List<Product> productViews = productViewService.getAllProductView();
        model.addAttribute("productViews", productViews);
        return "/user/shop";
    }

    @GetMapping("/product-detail/{id}")
    public String productDetail(@PathVariable Integer id, Model model){
        Product product = productViewService.getOne(id);
        List<Color> listColor = userColorService.getColorByProductId(id);
        List<Size> listSize = userSizeService.getSizeByProductId(id);

        model.addAttribute("product", product);
        model.addAttribute("listProductColor", listColor);
        model.addAttribute("listProductSize", listSize);
        return "/user/detail";
    }
}
