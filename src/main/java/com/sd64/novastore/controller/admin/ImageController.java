package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Image;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.service.ImageService;
import com.sd64.novastore.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/nova/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductDetailService productDetailService;

//    @GetMapping("/page")
//    public String getPage(@RequestParam(defaultValue = "0") Integer page, Model model) {
//        Page<Image> pageImage = imageService.getPage(page);
//        model.addAttribute("pageImage", pageImage);
//        model.addAttribute("page", page);
//        return "admin/image/image";
//    }

    @GetMapping("/{productDetailId}")
    public String getImage(@PathVariable Integer productDetailId,
                           Model model,
                           @RequestParam(defaultValue = "0") int page) {
        Page<Image> pageImage = imageService.getImageByProductDetail(page, productDetailId);
        ProductDetail productDetail = productDetailService.getOne(productDetailId);
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("pageImage", pageImage);
        model.addAttribute("productDetailId", productDetail.getId());
        return "admin/image/image";
    }

    @PostMapping("/add")
    public String add(@RequestParam(required = false) Integer productDetailId,
                      @RequestParam List<MultipartFile> images,
                      RedirectAttributes redirectAttributes) {
        imageService.add(productDetailId, images);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
        return "redirect:/nova/image/" + productDetailId;
    }

}
