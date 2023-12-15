package com.sd64.novastore.controller.user;

import com.sd64.novastore.model.*;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.SessionCart;
import com.sd64.novastore.repository.user.ProductViewRepository;
import com.sd64.novastore.response.ProductHomeResponse;
import com.sd64.novastore.service.*;
import com.sd64.novastore.service.user.ProductViewService;
import com.sd64.novastore.service.user.UserColorService;
import com.sd64.novastore.service.user.UserSizeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private MaterialService materialService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FormService formService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "0") int page,
                       Model model,
                       Principal principal,
                       HttpSession session) {
        if (principal != null) {
            Customer customer = customerService.findByEmail(principal.getName());
            session.setAttribute("username", customer.getName());
            Cart cart = customer.getCart();
            SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
            if (sessionCart != null) {
                if (sessionCart.getCartDetails() != null){
                    cartService.combineCart(sessionCart, principal.getName());
                    session.removeAttribute("sessionCart");
                }
            }
            if (cart != null) {
                session.setAttribute("totalItems", cart.getTotalItems());
            }
        }
        List<ProductHomeResponse> listProductHomeResponse = productViewService.getAllProductHomeResponse();
        model.addAttribute("pageProductHomeResponse", listProductHomeResponse);

        return "/user/home";
    }

    @GetMapping("/shop")
    public String shop(Model model,
                       @RequestParam(defaultValue = "0") int page) {
        Page<ProductHomeResponse> pageProductShopResponse = productViewService.getAllProductShopResponse(page);
        model.addAttribute("pageProductShopResponse", pageProductShopResponse);
        List<Material> listMaterial = materialService.getAll();
        List<Category> listCategory = categoryService.getAll();
        List<Form> listForm = formService.getAll();
        List<Brand> listBrand = brandService.getAll();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        model.addAttribute("listCategory", listCategory);
        return "/user/shop";
    }

    @Autowired
    private ProductViewRepository productViewRepository;

    @GetMapping("/search")
    public String searchProductResponse(@RequestParam(required = false) String productNameSearch,
                                        @RequestParam(required = false) Integer brandId,
                                        @RequestParam(required = false) Integer materialId,
                                        @RequestParam(required = false) Integer categoryId,
                                        @RequestParam(required = false) Integer formId,
                                        @RequestParam(required = false) Integer price,
                                        @RequestParam(defaultValue = "0") int page,
                                        Model model) {
        if (productNameSearch == "" && price == null && brandId == null && materialId == null && categoryId == null && formId == null) {
            return "redirect:/shop";
        }
        BigDecimal priceMax = productViewService.getPriceMaxBySelected(price);
        BigDecimal priceMin = productViewService.getPriceMinBySelected(price);
        Page<ProductHomeResponse> pageProductShopResponse = productViewService.searchProductShopResponse(brandId, categoryId, formId, materialId, productNameSearch, priceMax, priceMin, page);
        model.addAttribute("pageProductShopResponse", pageProductShopResponse);
        model.addAttribute("productName", productNameSearch);
        List<Material> listMaterial = materialService.getAll();
        List<Category> listCategory = categoryService.getAll();
        List<Form> listForm = formService.getAll();
        List<Brand> listBrand = brandService.getAll();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        model.addAttribute("brandId", brandId);
        model.addAttribute("materialId", materialId);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("formId", formId);
        model.addAttribute("priceMax", priceMax);
        model.addAttribute("priceMin", priceMin);
        model.addAttribute("productNameSearch", productNameSearch);
        return "user/shop";
    }

    @GetMapping("/product-detail/{productId}")
    public String productDetail(@PathVariable Integer productId, Model model) {
        Product product = productViewService.getOne(productId);

        Page<ProductHomeResponse> pageProductYouMayLikeResponse = productViewService.getAllProductHomeResponse(0);
        model.addAttribute("pageProductYouMayLikeResponse", pageProductYouMayLikeResponse);

        BigDecimal priceMax = productViewService.getPriceMaxResponseByProductId(productId);
        BigDecimal priceMin = productViewService.getPriceMinResponseByProductId(productId);
        var listProductSize = productViewService.getAllSizeDetailResponse(productId);
        var listProductColor = productViewService.getAllColorDetailResponse(productId);
        model.addAttribute("product", product);
        List<Image> listImage = imageService.getAllImageByProductId(productId);
        model.addAttribute("listImage", listImage);
        model.addAttribute("imageActive", imageService.getImageActiveByProductId(productId));
        model.addAttribute("listProductColor", listProductColor);
        model.addAttribute("listProductSize", listProductSize);
        model.addAttribute("priceMax", priceMax);
        model.addAttribute("priceMin", priceMin);
        return "/user/detail";
    }
}
