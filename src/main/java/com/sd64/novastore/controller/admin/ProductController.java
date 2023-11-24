package com.sd64.novastore.controller.admin;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.model.*;
import com.sd64.novastore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
        List<Material> listMaterial = materialService.getAll();
        List<Category> listCategory = categoryService.getAll();
        List<Form> listForm = formService.getAll();
        List<Brand> listBrand = brandService.getAll();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        return "admin/product/product";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        List<Material> listMaterial = materialService.getAll();
        List<Category> listCategory = categoryService.getAll();
        List<Form> listForm = formService.getAll();
        List<Brand> listBrand = brandService.getAll();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        return "admin/product/product-add";
    }


    @PostMapping("/add")
    public String add(@RequestParam String code,
                      @RequestParam String name,
                      @RequestParam String description,
                      @RequestParam BigDecimal price,
                      @RequestParam Integer materialId,
                      @RequestParam Integer categoryId,
                      @RequestParam Integer brandId,
                      @RequestParam Integer formId,
                      RedirectAttributes redirectAttributes) {
        if (productService.add(code, name, description, price, materialId, categoryId, brandId, formId)) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
        } else {
            redirectAttributes.addFlashAttribute("error", "Mã sản phẩm đã tồn tại, Thêm dữ liệu thất bại");
        }
        return "redirect:/nova/product/page";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable Integer id, Model model) {
        Product product = productService.getOne(id);
        model.addAttribute("productUpdate", product);
        List<Material> listMaterial = materialService.getAll();
        List<Category> listCategory = categoryService.getAll();
        List<Form> listForm = formService.getAll();
        List<Brand> listBrand = brandService.getAll();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        return "admin/product/product-update";
    }


    @PostMapping("/update/{id}")
    public String update(@RequestParam String code,
                         @PathVariable Integer id,
                         @RequestParam String name,
                         @RequestParam String description,
                         @RequestParam BigDecimal price,
                         @RequestParam Integer materialId,
                         @RequestParam Integer categoryId,
                         @RequestParam Integer brandId,
                         @RequestParam Integer formId,
                         RedirectAttributes redirectAttributes) {
        productService.update(id, code, name, description, price, materialId, categoryId, brandId, formId);
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
//        if (priceMin != null && priceMax != null) {
//
//        }
        Page<ProductDto> pageProductDto = productService.search(materialId, brandId, formId, categoryId, productName, description, priceMin, priceMax, page);
        model.addAttribute("pageProduct", pageProductDto);
        model.addAttribute("productName", productName);
        model.addAttribute("description", description);
        List<Material> listMaterial = materialService.getAll();
        List<Category> listCategory = categoryService.getAll();
        List<Form> listForm = formService.getAll();
        List<Brand> listBrand = brandService.getAll();
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

    @PostMapping("/excel")
    public String importExcel(RedirectAttributes redirectAttributes, @RequestParam MultipartFile excelFile) throws IOException {
        if (productService.importExcelProduct(excelFile).contains("Trùng mã")) {
            redirectAttributes.addFlashAttribute("error", "Mã sản phẩm trong file đã tồn tại");
            return "redirect:/nova/product/page";
        } else if (productService.importExcelProduct(excelFile).contains("Sai dữ liệu")) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng kiểm tra lại dữ liệu trong file");
            return "redirect:/nova/product/page";
        } else if (productService.importExcelProduct(excelFile).contains("Lỗi file")) {
            redirectAttributes.addFlashAttribute("error", "Đây không phải là file excel");
            return "redirect:/nova/product/page";
        } else {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu excel thành công");
            return "redirect:/nova/product/page";
        }
    }

    @GetMapping("/view-restore")
    public String viewRestore(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<ProductDto> pageProductDto = productService.getAllProductDeleted(page);
        model.addAttribute("pageProduct", pageProductDto);
        List<Material> listMaterial = materialService.getAll();
        List<Category> listCategory = categoryService.getAll();
        List<Form> listForm = formService.getAll();
        List<Brand> listBrand = brandService.getAll();
        model.addAttribute("listMaterial", listMaterial);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listForm", listForm);
        model.addAttribute("listBrand", listBrand);
        return "admin/product/product-restore";
    }

    @PostMapping("/restore")
    public String restore(@RequestParam List<Integer> listProductId,
                          RedirectAttributes redirectAttributes) {
        productService.restore(listProductId);
        redirectAttributes.addFlashAttribute("mess", "Khôi phục sản phẩm thành công");
        return "redirect:/nova/product/view-restore";
    }

    @GetMapping("/search-restore")
    public String searchProductDelete(@RequestParam(required = false) String productName,
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
//        if (priceMin != null && priceMax != null) {
//
//        }
        Page<ProductDto> pageProductDto = productService.searchProductDeleted(materialId, brandId, formId, categoryId, productName, description, priceMin, priceMax, page);
        model.addAttribute("pageProduct", pageProductDto);
        model.addAttribute("productName", productName);
        model.addAttribute("description", description);
        List<Material> listMaterial = materialService.getAll();
        List<Category> listCategory = categoryService.getAll();
        List<Form> listForm = formService.getAll();
        List<Brand> listBrand = brandService.getAll();
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
        return "admin/product/product-restore";
    }
}
