package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Image;
import com.sd64.novastore.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("/nova/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Image> pageImage = imageService.getPage(page);
        model.addAttribute("pageImage", pageImage);
        model.addAttribute("page", page);
        return "admin/image/image";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Image image, RedirectAttributes redirectAttributes) {
        imageService.add(image);
        redirectAttributes.addFlashAttribute("mess", "Thêm thành công");
        return "redirect:/nova/form/page";
    }

}
