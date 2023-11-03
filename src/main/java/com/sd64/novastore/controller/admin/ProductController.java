package com.sd64.novastore.controller.admin;

import com.sd64.novastore.dto.ProductDto;
import com.sd64.novastore.model.*;
import com.sd64.novastore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/nova/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private FormService formService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public String getPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<ProductDto> pageProductDto = productService.getAll(page);
        model.addAttribute("pageProduct", pageProductDto);
        List<Material> listMaterial = materialService.getAllDefault();
        List<Category> listCategory = categoryService.getAllDefault();
        List<Form> listForm = formService.getAllDefault();
        List<Brand> listBrand = brandService.getAllDefault();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        return "admin/product/product";
    }

    @GetMapping("/view-add")
    public String viewAdd (Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        List<Material> listMaterial = materialService.getAllDefault();
        List<Category> listCategory = categoryService.getAllDefault();
        List<Form> listForm = formService.getAllDefault();
        List<Brand> listBrand = brandService.getAllDefault();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        return "admin/product/product-add";
    }


    @PostMapping("/add")
    public String add(@RequestParam String name,
                      @RequestParam String description,
                      @RequestParam BigDecimal price,
                      @RequestParam Integer materialId,
                      @RequestParam Integer categoryId,
                      @RequestParam Integer brandId,
                      @RequestParam Integer formId,
                      RedirectAttributes redirectAttributes) {
        productService.add(name, description, price, materialId, categoryId, brandId, formId);
        redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
        return "redirect:/nova/product/page";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable Integer id, Model model) {
        Product product = productService.getOne(id);
        model.addAttribute("productUpdate", product);
        List<Material> listMaterial = materialService.getAllDefault();
        List<Category> listCategory = categoryService.getAllDefault();
        List<Form> listForm = formService.getAllDefault();
        List<Brand> listBrand = brandService.getAllDefault();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        return "admin/product/product-update";
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @RequestParam String name,
                         @RequestParam String description,
                         @RequestParam BigDecimal price,
                         @RequestParam Integer materialId,
                         @RequestParam Integer categoryId,
                         @RequestParam Integer brandId,
                         @RequestParam Integer formId,
                         RedirectAttributes redirectAttributes) {
        productService.update(id, name, description, price, materialId, categoryId, brandId, formId);
        redirectAttributes.addFlashAttribute("mess", "Sửa dữ liệu thành công");
        return "redirect:/nova/product/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa dữ liệu thành công");
        return "redirect:/nova/product/page";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String productName,
                         @RequestParam(required = false) Integer brandId,
                         @RequestParam(required = false) Integer materialId,
                         @RequestParam(required = false) Integer categoryId,
                         @RequestParam(required = false) Integer formId,
                         @RequestParam(required = false) String description,
                         @RequestParam(required = false) BigDecimal priceMin,
                         @RequestParam(required = false) BigDecimal priceMax,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        if (priceMin == null) {
            priceMin = BigDecimal.valueOf(0);
            model.addAttribute("priceMinNull", null);
        }
        if (priceMax == null) {
            priceMax = BigDecimal.valueOf(Integer.MAX_VALUE);
            model.addAttribute("priceMaxNull", null);
        }
        if (priceMin != null && priceMax != null) {

        }
        Page<ProductDto> pageProductDto = productService.search(materialId, brandId, formId, categoryId, productName, description, priceMin, priceMax, page);
        model.addAttribute("pageProduct", pageProductDto);
        model.addAttribute("productName", productName);
        model.addAttribute("description", description);
        List<Material> listMaterial = materialService.getAllDefault();
        List<Category> listCategory = categoryService.getAllDefault();
        List<Form> listForm = formService.getAllDefault();
        List<Brand> listBrand = brandService.getAllDefault();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        model.addAttribute("brandId", brandId);
        model.addAttribute("materialId", materialId);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("formId", formId);
        model.addAttribute("priceMin", priceMin);
        model.addAttribute("priceMax", priceMax);
        return "admin/product/product";
    }
}
