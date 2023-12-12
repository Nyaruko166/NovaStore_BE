package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Material;
import com.sd64.novastore.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/nova/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Material> pageMaterial = materialService.getPage(page);
        model.addAttribute("pageMaterial", pageMaterial);
        model.addAttribute("page", page);
        return "admin/material/material";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Material material = materialService.detail(id);
        model.addAttribute("material", material);
        return "/admin/material/material-detail";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name,
                      RedirectAttributes redirectAttributes) {
        if (materialService.add(name)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/material/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên chất liệu đã tồn tại");
            return "redirect:/nova/material/page";
        }
    }

    @PostMapping("/attribute")
    public String attribute(@RequestParam String materialName,
                            RedirectAttributes redirectAttributes) {
        if (materialService.add(materialName)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/product/view-add";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên chất liệu đã tồn tại");
            return "redirect:/nova/product/view-add";
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute Material material,
                         RedirectAttributes redirectAttributes) {
        if (materialService.update(material, id)) {
            redirectAttributes.addFlashAttribute("mess", "Sửa dữ liệu thành công");
            return "redirect:/nova/material/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên chất liệu đã tồn tại");
            return "redirect:/nova/material/detail/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        materialService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa dữ liệu thành công");
        return "redirect:/nova/material/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String materialNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Material> pageMaterial = materialService.search(materialNameSearch, page);
        if ("".equals(materialNameSearch) || materialNameSearch.isEmpty()) {
            return "redirect:/nova/material/page";
        }
        model.addAttribute("materialNameSearch", materialNameSearch);
        model.addAttribute("pageMaterial", pageMaterial);
        return "admin/material/material";
    }

    @GetMapping("/view-restore")
    public String viewRestore(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Material> pageMaterial = materialService.getAllDeleted(page);
        model.addAttribute("pageMaterial", pageMaterial);
        return "admin/material/material-restore";
    }

    @PostMapping("/restore/{id}")
    public String restore(@PathVariable Integer id,
                          RedirectAttributes redirectAttributes) {
        materialService.restore(id);
        redirectAttributes.addFlashAttribute("mess", "Khôi phục dữ liệu thành công");
        return "redirect:/nova/material/view-restore";
    }

    @GetMapping("/search-restore")
    public String searchDelete(Model model, @RequestParam(required = false) String materialNameSearch,
                               @RequestParam(defaultValue = "0") int page) {
        Page<Material> pageMaterial = materialService.searchDelete(materialNameSearch, page);
        if ("".equals(materialNameSearch) || materialNameSearch.isEmpty()) {
            return "redirect:/nova/material/view-restore";
        }
        model.addAttribute("materialNameSearch", materialNameSearch);
        model.addAttribute("pageMaterial", pageMaterial);
        return "admin/material/material-restore";
    }

    @PostMapping("/excel")
    public String importExcel(RedirectAttributes redirectAttributes, @RequestParam MultipartFile excelFile) throws IOException {
        if (materialService.importExcel(excelFile) == 1) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu excel thành công");
            return "redirect:/nova/material/page";
        } else if (materialService.importExcel(excelFile) == -1) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng kiểm tra lại dữ liệu trong file");
            return "redirect:/nova/material/page";
        } else if (materialService.importExcel(excelFile) == 2) {
            redirectAttributes.addFlashAttribute("error", "Dữ liệu đang bị trùng");
            return "redirect:/nova/material/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Đây không phải là file excel");
            return "redirect:/nova/material/page";
        }
    }
}
