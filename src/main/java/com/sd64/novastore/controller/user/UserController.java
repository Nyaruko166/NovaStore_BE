package com.sd64.novastore.controller.user;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.service.ColorService;
import com.sd64.novastore.service.SizeService;
import com.sd64.novastore.service.user.ProductViewService;
import com.sd64.novastore.service.user.UserColorService;
import com.sd64.novastore.service.user.UserProductDetailService;
import com.sd64.novastore.service.user.UserSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private ProductViewService productViewService;

    @Autowired
    private UserProductDetailService userProductDetailService;

    @Autowired
    private UserSizeService userSizeService;

    @Autowired
    private UserColorService userColorService;

    @GetMapping("/home")
    public String home(Model model){
        List<Product> productViews = productViewService.getAllProductView();
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
        List<Color> listProductColor = userColorService.getColorByProductId(id);
        List<Size> listProductSize = userSizeService.getSizeByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("listProductColor", listProductColor);
        model.addAttribute("listProductSize", listProductSize);
        return "/user/detail";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("color") Integer colorId,
                            @RequestParam("size") Integer sizeId,
                            @RequestParam("quantity") Integer quantity,
                            @RequestParam("productId") Integer productId){
        Integer productDetailId = userProductDetailService.getProductDetailId(productId, sizeId, colorId);
        System.out.println(productDetailId);

        String redirectUrl = String.format("redirect:/product-detail/%d", productId);
        return redirectUrl;
    }
}
