package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Size;
import com.sd64.novastore.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/nova/size")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Size> pageSize = sizeService.getPage(page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        return "admin/size/size";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id,
                         Model model) {
        Size size = sizeService.detail(id);
        model.addAttribute("size", size);
        return "/admin/size/size-detail";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name,
                      RedirectAttributes redirectAttributes) {
        if (sizeService.add(name)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/size/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Kích cỡ đã tồn tại");
            return "redirect:/nova/size/page";
        }
    }

    @PostMapping("//update/{id}")
    public String update(@PathVariable Integer id,
                         @RequestParam String name,
                         RedirectAttributes redirectAttributes) {
        if (sizeService.update(id, name)) {
            redirectAttributes.addFlashAttribute("mess", "Sửa dữ liệu thành công");
            return "redirect:/nova/size/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên kích cỡ đã tồn tại");
            return "redirect:/nova/size/page";
        }
    }

    @PostMapping("/{productId}/size/delete/{id}")
    public String delete(@PathVariable Integer productId,
                         @PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        sizeService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa dữ liệu thành công");
        return "redirect:/nova/size/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String sizeNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Size> pageSize = sizeService.search(sizeNameSearch, page);
        if ("".equals(sizeNameSearch) || sizeNameSearch.isEmpty()) {
            return "redirect:/nova/size/page";
        }
        model.addAttribute("sizeNameSearch", sizeNameSearch);
        model.addAttribute("pageSize", pageSize);
        return "admin/size/size";
    }

    @GetMapping("/view-restore")
    public String viewRestore(@RequestParam(defaultValue = "0") int page,
                              Model model) {
        Page<Size> pageSize = sizeService.getAllSizeDelete(page);
        model.addAttribute("pageSize", pageSize);
        return "admin/size/size-restore";
    }

    @PostMapping("restore/{id}")
    public String restore(@PathVariable Integer id,
                          RedirectAttributes redirectAttributes) {
        sizeService.restore(id);
        redirectAttributes.addFlashAttribute("mess", "Khôi phục dữ liệu thành công");
        return "redirect:/nova/size/view-restore";
    }

    @GetMapping("/search-restore")
    public String searchDelete(Model model, @RequestParam(required = false) String sizeNameSearch,
                               @RequestParam(defaultValue = "0") int page) {
        Page<Size> pageSize = sizeService.searchDeleted(sizeNameSearch, page);
        if ("".equals(sizeNameSearch) || sizeNameSearch.isEmpty()) {
            return "redirect:/nova/size/view-restore";
        }
        model.addAttribute("sizeNameSearch", sizeNameSearch);
        model.addAttribute("pageSize", pageSize);
        return "admin/size/size-restore";
    }
}
