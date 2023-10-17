package com.sd64.novastore.controller.admin;

import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.Role;
import com.sd64.novastore.request.PromotionRequest;
import com.sd64.novastore.service.PromotionService;
import com.sd64.novastore.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Role> listRole = roleService.getAll();
        model.addAttribute("listRole", listRole);
        return "";
    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Role> pageRole = roleService.getPage(page);
        model.addAttribute("pageRole", pageRole.getContent());
        return "";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("Role") Role role) {
        roleService.add(role);
        return "redirect:/admin/role/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @Validated @ModelAttribute("Role") Role role) {
        roleService.update(role, id);
        return "redirect:/admin/role/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        roleService.delete(id);
        return "redirect:/admin/role/page";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String nameSearch,
                         @RequestParam(defaultValue = "0") int page) {
        Page<Role> listRole = roleService.search(nameSearch, page);
        model.addAttribute("listRole", listRole.getContent());
        return "";
    }
}
