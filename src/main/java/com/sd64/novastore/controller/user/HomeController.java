package com.sd64.novastore.controller.user;

import com.sd64.novastore.dto.common.ProductResponseHomeDto;
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
import com.sd64.novastore.service.ProductDetailService;
import com.sd64.novastore.service.user.ProductViewService;
import com.sd64.novastore.service.user.UserColorService;
import com.sd64.novastore.service.user.UserSizeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductViewService productViewService;

    @Autowired
    private UserSizeService userSizeService;

    @Autowired
    private UserColorService userColorService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductDetailService productDetailService;

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
        ProductResponseHomeDto productViews = productViewService.getProductResponse();
        model.addAttribute("productViews", productViews);
        BigDecimal priceMax = productDetailService.getPriceMaxByProductId(productViews.getProductId());
        model.addAttribute("priceMax", priceMax);
        BigDecimal priceMin = productDetailService.getPriceMinByProductId(productViews.getProductId());
        model.addAttribute("priceMin", priceMin);
        return "/user/home";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        List<Product> productViews = productViewService.getAllProductView();
        model.addAttribute("productViews", productViews);
        return "/user/shop";
    }

    @GetMapping("/product-detail/{productId}")
    public String productDetail(@PathVariable Integer productId, Model model){
        Product product = productViewService.getOne(productId);
        List<Color> listColor = userColorService.getColorByProductId(productId);
        List<Size> listSize = userSizeService.getSizeByProductId(productId);

//        List<ProductResponseHomeDto> productDetail = productViewService.getAllProductResponse();
        List<ProductDetail> listProductDetail = productDetailService.getProductDetailByProductId(productId);
        model.addAttribute("listProductDetail", listProductDetail);
        model.addAttribute("product", product);
        List<Image> listImage = imageService.getAllImageByProductId(productId);
        model.addAttribute("listImage", listImage);
        model.addAttribute("imageActive", imageService.getImageActiveByProductId(productId));
        model.addAttribute("listProductColor", listColor);
        model.addAttribute("listProductSize", listSize);
        BigDecimal priceMin = productDetailService.getPriceMinByProductId(productId);
        model.addAttribute("priceMin", priceMin);
        return "/user/detail";
    }
}
