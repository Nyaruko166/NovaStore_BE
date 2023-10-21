package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/size")
public class SizeController {

    @Autowired
    private SizeService sizeService;

//    @GetMapping("/all")
//    public String getAll(Model model) {
//        List<Size> listSize = sizeService.getAll();
//        model.addAttribute("listSize", listSize);
//        return "";
//    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Size> pageSize = sizeService.getPage(page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        return "admin/size/size";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Size size = sizeService.detail(id);
        model.addAttribute("size", size);
        return "/admin/size/size-detail";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Size size, RedirectAttributes redirectAttributes) {
        sizeService.add(size);
        redirectAttributes.addFlashAttribute("add", "Thêm thành công");
        return "redirect:/admin/size/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Size size, RedirectAttributes redirectAttributes) {
        sizeService.update(size, id);
        redirectAttributes.addFlashAttribute("update", "Sửa thành công");
        return "redirect:/admin/size/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        sizeService.delete(id);
        redirectAttributes.addFlashAttribute("delete", "Xóa thành công");
        return "redirect:/admin/size/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String sizeNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Size> pageSize = sizeService.search(sizeNameSearch, page);
        if ("".equals(sizeNameSearch) || sizeNameSearch.isEmpty()) {
            return "redirect:/admin/size/page";
        }
        model.addAttribute("sizeNameSearch", sizeNameSearch);
        model.addAttribute("pageSize", pageSize);
        return "admin/size/size";
    }
}
