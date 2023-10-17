package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Form;
import com.sd64.novastore.request.FormRequest;
import com.sd64.novastore.service.FormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/form")
public class FormController {
    @Autowired
    private FormService formService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Form> listForm = formService.getAll();
        model.addAttribute("listForm", listForm);
        return "";
    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Form> pageForm = formService.getPage(page);
        model.addAttribute("pageForm", pageForm.getContent());
        return "";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Form") Form form) {
        formService.add(form);
        return "redirect:/admin/form/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute("Form") Form form) {
        formService.update(form, id);
        return "redirect:/admin/form/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        formService.delete(id);
        return "redirect:/admin/form/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String nameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Form> listForm = formService.search(nameSearch, page);
        model.addAttribute("listForm", listForm.getContent());
        return "";
    }
}
