package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Form;
import com.sd64.novastore.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/nova/form")
public class FormController {
    @Autowired
    private FormService formService;

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Form> pageForm = formService.getPage(page);
        model.addAttribute("pageForm", pageForm);
        model.addAttribute("page", page);
        return "admin/form/form";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Form form = formService.detail(id);
        model.addAttribute("form", form);
        return "/admin/form/form-detail";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name,
                      RedirectAttributes redirectAttributes) {
        if (formService.add(name)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/form/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên kiểu dáng đã tồn tại");
            return "redirect:/nova/form/page";
        }
    }

    @PostMapping("/attribute")
    public String attribute(@RequestParam String formName,
                            RedirectAttributes redirectAttributes) {
        if (formService.add(formName)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
            return "redirect:/nova/product/view-add";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên chất liệu đã tồn tại");
            return "redirect:/nova/product/view-add";
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute Form form,
                         RedirectAttributes redirectAttributes) {
        if (formService.update(form, id)) {
            redirectAttributes.addFlashAttribute("mess", "Sửa dữ liệu thành công");
            return "redirect:/nova/form/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Tên kiểu dáng đã tồn tại");
            return "redirect:/nova/form/detail/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        formService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa dữ liệu thành công");
        return "redirect:/nova/form/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String formNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Form> pageForm = formService.search(formNameSearch, page);
        if ("".equals(formNameSearch) || formNameSearch.isEmpty()) {
            return "redirect:/nova/form/page";
        }
        model.addAttribute("formNameSearch", formNameSearch);
        model.addAttribute("pageForm", pageForm);
        return "admin/form/form";
    }

    @GetMapping("/view-restore")
    public String viewRestore(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Page<Form> pageForm = formService.getAllDeleted(page);
        model.addAttribute("pageForm", pageForm);
        return "admin/form/form-restore";
    }

    @PostMapping("/restore/{id}")
    public String restore(@PathVariable Integer id,
                          RedirectAttributes redirectAttributes) {
        formService.restore(id);
        redirectAttributes.addFlashAttribute("mess", "Khôi phục dữ liệu thành công");
        return "redirect:/nova/form/view-restore";
    }

    @GetMapping("/search-restore")
    public String searchDelete(Model model, @RequestParam(required = false) String formNameSearch,
                               @RequestParam(defaultValue = "0") int page) {
        Page<Form> pageForm = formService.searchDelete(formNameSearch, page);
        if ("".equals(formNameSearch) || formNameSearch.isEmpty()) {
            return "redirect:/nova/form/view-restore";
        }
        model.addAttribute("formNameSearch", formNameSearch);
        model.addAttribute("pageForm", pageForm);
        return "admin/form/form-restore";
    }

    @PostMapping("/excel")
    public String importExcel(RedirectAttributes redirectAttributes, @RequestParam MultipartFile excelFile) throws IOException {
        String result = formService.importExcel(excelFile);
        if (result.contains("Oke")) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu excel thành công");
            return "redirect:/nova/form/page";
        } else if (result.contains("Sai dữ liệu")) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng kiểm tra lại kiểu dữ liệu trong file");
            return "redirect:/nova/form/page";
        } else if (result.contains("Tồn tại")) {
            redirectAttributes.addFlashAttribute("error", "Dữ liệu trong file đã tồn tại");
            return "redirect:/nova/form/page";
        } else if (result.contains("Trống")) {
            redirectAttributes.addFlashAttribute("error", "Trong file excel không có dữ liệu");
            return "redirect:/nova/brand/page";
        } else if (result.contains("Trùng")) {
            redirectAttributes.addFlashAttribute("error", "1 số dữ liệu trong file bị trùng lặp");
            return "redirect:/nova/form/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Đây không phải là file excel");
            return "redirect:/nova/form/page";
        }
    }
}
