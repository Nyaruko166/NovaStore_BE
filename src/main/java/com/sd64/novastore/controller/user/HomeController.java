package com.sd64.novastore.controller.user;

import com.sd64.novastore.dto.common.ProductDetailAndValueDiscountDto;
import com.sd64.novastore.dto.common.impl.ProductDetailDiscountDtoImpl;
import com.sd64.novastore.model.*;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.SessionCart;
import com.sd64.novastore.repository.user.ProductViewRepository;
import com.sd64.novastore.response.ProductDiscountHomeResponse;
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
            session.setAttribute("role", customer.getRole().getName());
            Cart cart = cartService.getCart(principal.getName());
            SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
            if (sessionCart != null) {
                if (sessionCart.getCartDetails() != null) {
                    cartService.combineCart(sessionCart, principal.getName());
                    session.removeAttribute("sessionCart");
                }
            }
            if (cart != null) {
                session.setAttribute("totalItems", cart.getTotalItems());
            }
        }
        List<ProductDiscountHomeResponse> listProducAndDiscountHomeResponse = productViewService.getAllProductAndProductDiscountHomeResponse();
        model.addAttribute("listProducAndDiscountHomeResponse", productViewService.setPriceDiscount(listProducAndDiscountHomeResponse));
//        model.addAttribute("listProducAndDiscountHomeResponse", listProducAndDiscountHomeResponse);
        List<ProductDiscountHomeResponse> listProductDiscountHomeResponse = productViewService.getAllProductDiscountHomeResponse();
        model.addAttribute("listProductDiscountHomeResponse", productViewService.setPriceDiscount(listProductDiscountHomeResponse));

        return "/user/home";
    }

    @GetMapping("/shop")
    public String shop(Model model,
                       @RequestParam(defaultValue = "0") int page) {
        List<ProductDiscountHomeResponse> listProductDiscountHomeResponse = productViewService.getAllProductAndProductDiscountShopResponse();
        Page<ProductDiscountHomeResponse> pageProductDiscountHomeResponse = productViewService.convertlistToPage(productViewService.setPriceDiscount(listProductDiscountHomeResponse), page);
        model.addAttribute("pageProductShopResponse", pageProductDiscountHomeResponse);
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

    @GetMapping("/search")
    public String searchProductResponse(@RequestParam(required = false) String productNameSearch,
                                        @RequestParam(required = false) Integer sort,
                                        @RequestParam(required = false) Integer brandId,
                                        @RequestParam(required = false) Integer materialId,
                                        @RequestParam(required = false) Integer categoryId,
                                        @RequestParam(required = false) Integer formId,
                                        @RequestParam(required = false) Integer price,
                                        @RequestParam(defaultValue = "0") int page,
                                        Model model) {
        if (productNameSearch == "" && sort == null && price == null && brandId == null && materialId == null && categoryId == null && formId == null) {
            return "redirect:/shop";
        }
        if (sort == null) {
            BigDecimal priceMax = productViewService.getPriceMaxBySelected(price);
            BigDecimal priceMin = productViewService.getPriceMinBySelected(price);
            List<ProductDiscountHomeResponse> listProductDiscountHomeResponse = productViewService.searchProductAndProductDiscountShopResponse(brandId, categoryId, formId, materialId, productNameSearch, priceMax, priceMin, page);
            Page<ProductDiscountHomeResponse> pageProductDiscountHomeResponse = productViewService.convertlistToPage(productViewService.setPriceDiscount(listProductDiscountHomeResponse), page);
            model.addAttribute("pageProductShopResponse", pageProductDiscountHomeResponse);
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
            model.addAttribute("sort", sort);
            model.addAttribute("price", price);
            model.addAttribute("productNameSearch", productNameSearch);
            return "user/shop";
        } else if (sort == 1) {
            BigDecimal priceMax = productViewService.getPriceMaxBySelected(price);
            BigDecimal priceMin = productViewService.getPriceMinBySelected(price);
            List<ProductDiscountHomeResponse> listProductDiscountHomeResponse = productViewService.searchProductAndProductDiscountDescShopResponse(brandId, categoryId, formId, materialId, productNameSearch, priceMax, priceMin, page);
            Page<ProductDiscountHomeResponse> pageProductDiscountHomeResponse = productViewService.convertlistToPage(productViewService.setPriceDiscount(listProductDiscountHomeResponse), page);
            model.addAttribute("pageProductShopResponse", pageProductDiscountHomeResponse);
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
            model.addAttribute("sort", sort);
            model.addAttribute("price", price);
            model.addAttribute("productNameSearch", productNameSearch);
            return "user/shop";
        } else if (sort == 2) {
            BigDecimal priceMax = productViewService.getPriceMaxBySelected(price);
            BigDecimal priceMin = productViewService.getPriceMinBySelected(price);
            List<ProductDiscountHomeResponse> listProductDiscountHomeResponse = productViewService.searchProductAscShopResponse(brandId, categoryId, formId, materialId, productNameSearch, priceMax, priceMin, page);
            Page<ProductDiscountHomeResponse> pageProductDiscountHomeResponse = productViewService.convertlistToPage(productViewService.setPriceDiscount(listProductDiscountHomeResponse), page);
            model.addAttribute("pageProductShopResponse", pageProductDiscountHomeResponse);
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
            model.addAttribute("sort", sort);
            model.addAttribute("price", price);
            model.addAttribute("productNameSearch", productNameSearch);
            return "user/shop";
        } else {
            BigDecimal priceMax = productViewService.getPriceMaxBySelected(price);
            BigDecimal priceMin = productViewService.getPriceMinBySelected(price);
            List<ProductDiscountHomeResponse> listProductDiscountHomeResponse = productViewService.searchOnlyProductDiscountShopResponse(brandId, categoryId, formId, materialId, productNameSearch, priceMax, priceMin, page);
            Page<ProductDiscountHomeResponse> pageProductDiscountHomeResponse = productViewService.convertlistToPage(productViewService.setPriceDiscount(listProductDiscountHomeResponse), page);
            model.addAttribute("pageProductShopResponse", pageProductDiscountHomeResponse);
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
            model.addAttribute("sort", sort);
            model.addAttribute("price", price);
            model.addAttribute("productNameSearch", productNameSearch);
            return "user/shop";
        }
    }

    @GetMapping("/product-detail/{productId}")
    public String productDetail(@PathVariable Integer productId, Model model) {
        Product product = productViewService.getOne(productId);
        List<ProductDiscountHomeResponse> listProductYouMayLikeResponse = productViewService.getRandomProductAndProductDiscount();
        model.addAttribute("listProductYouMayLikeResponse", listProductYouMayLikeResponse);
        ProductDetailAndValueDiscountDto productDetailAndValueDiscountDto = productViewService.getProductDetailAndValueDiscount(productId);
        Float value = productViewService.getValueDiscountByProductId(productId);
        BigDecimal priceMax = productViewService.getPriceMaxResponseByProductId(productId);
        BigDecimal priceMin = productViewService.getPriceMinResponseByProductId(productId);

        if (productDetailAndValueDiscountDto != null) {
            BigDecimal priceDiscountMax = productViewService.calculatePriceToPriceDiscount(priceMax, productDetailAndValueDiscountDto.getValue());
            BigDecimal priceDiscountMin = productViewService.calculatePriceToPriceDiscount(priceMin, productDetailAndValueDiscountDto.getValue());
            model.addAttribute("priceDiscountMax", priceDiscountMax);
            model.addAttribute("priceDiscountMin", priceDiscountMin);
        } else {
            BigDecimal priceDiscountMax = productViewService.getPriceDiscountMaxResponseByProductId(productId);
            BigDecimal priceDiscountMin = productViewService.getPriceDiscountMinResponseByProductId(productId);
            model.addAttribute("priceDiscountMax", priceDiscountMax);
            model.addAttribute("priceDiscountMin", priceDiscountMin);
        }

//        BigDecimal priceDiscountMax = productViewService.getPriceDiscountMaxResponseByProductId(productId);
//        BigDecimal priceDiscountMin = productViewService.getPriceDiscountMinResponseByProductId(productId);
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
//        model.addAttribute("priceDiscountMax", priceDiscountMax);
//        model.addAttribute("priceDiscountMin", priceDiscountMin);
        var discount = ProductDetailDiscountDtoImpl.toResponse(productDetailAndValueDiscountDto);
        model.addAttribute("discount", discount);
        model.addAttribute("value", value);
        return "/user/detail";
    }
}
