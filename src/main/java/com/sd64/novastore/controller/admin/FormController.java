package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Form;
import com.sd64.novastore.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/form")
public class FormController {
    @Autowired
    private FormService formService;

//    @GetMapping("/all")
//    public String getAll(Model model) {
//        List<Form> listForm = formService.getAll();
//        model.addAttribute("listForm", listForm);
//        return "";
//    }

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
    public String add(@ModelAttribute Form form, RedirectAttributes redirectAttributes) {
        formService.add(form);
        redirectAttributes.addFlashAttribute("add", "Thêm thành công");
        return "redirect:/admin/form/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute Form form, RedirectAttributes redirectAttributes) {
        formService.update(form, id);
        redirectAttributes.addFlashAttribute("update", "Sửa thành công");
        return "redirect:/admin/form/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        formService.delete(id);
        redirectAttributes.addFlashAttribute("delete", "Xóa thành công");
        return "redirect:/admin/form/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String formNameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Form> pageForm = formService.search(formNameSearch, page);
        if ("".equals(formNameSearch) || formNameSearch.isEmpty()) {
            return "redirect:/admin/form/page";
        }
        model.addAttribute("formNameSearch", formNameSearch);
        model.addAttribute("pageForm", pageForm);
        return "admin/form/form";
    }
}
