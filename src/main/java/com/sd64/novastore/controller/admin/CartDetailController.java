package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.service.CartDetailService;
import com.sd64.novastore.service.CartService;
import com.sd64.novastore.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/cart_detail")
public class CartDetailController {
    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        CartDetail cartDetail = cartDetailService.getOne(id);
        List<Cart> cartList = cartService.getAllCart();
        List<ProductDetail> productDetailList = productDetailService.getAllProductDetail();
        model.addAttribute("cartList", cartList);
        model.addAttribute("productDetailList", productDetailList);
        model.addAttribute("cartDetail", cartDetail);
        return "/admin/cartdetail/cartdetail-detail";
    }

    @GetMapping("/page")
    public String getAllPT(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<CartDetail> page1 = cartDetailService.getAllPT(page);
        model.addAttribute("page", page1);
        return "/admin/cartdetail/cartdetail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        cartDetailService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá thành công!!");
        return "redirect:/admin/cart_detail/page";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("CartDetail") CartDetail cartDetail, RedirectAttributes redirectAttributes) {
        cartDetailService.add(cartDetail);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công!!");
        return "redirect:/admin/cart_detail/page";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("CartDetail") CartDetail cartDetail, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        cartDetailService.update(cartDetail, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa thành công!!");
        return "redirect:/admin/cart_detail/page";

    }
}
