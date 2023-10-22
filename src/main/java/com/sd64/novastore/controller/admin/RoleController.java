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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

//    @GetMapping("/all")
//    public String getAll(Model model) {
//        List<Role> listRole = roleService.getAll();
//        model.addAttribute("listRole", listRole);
//        return "";
//    }

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0", value = "page") Integer page, Model model) {
        Page<Role> pageRole = roleService.getPage(page);
        model.addAttribute("page", pageRole.getContent());
        return "/admin/role/role";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("Role") Role role, RedirectAttributes redirectAttributes) {
        roleService.add(role);
        redirectAttributes.addFlashAttribute("mess", "Thêm Thành Công!!");
        return "redirect:/admin/role/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute("Role") Role role,
                         RedirectAttributes redirectAttributes) {
        roleService.update(role, id);
        redirectAttributes.addFlashAttribute("mess", "Sửa Thành Công!!");
        return "redirect:/admin/role/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        roleService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xoá Thành Công!!");
        return "redirect:/admin/role/page";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("role", roleService.getOne(id));
        return "/admin/role/role-detail";
    }

//    @GetMapping("/search")
//    public String search(Model model, @RequestParam(required = false) String nameSearch,
//                         @RequestParam(defaultValue = "0") int page) {
//        Page<Role> listRole = roleService.search(nameSearch, page);
//        model.addAttribute("listRole", listRole.getContent());
//        return "";
//    }
}
