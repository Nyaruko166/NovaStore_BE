package com.sd64.novastore.controller.admin;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.model.*;
import com.sd64.novastore.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private ProductDetailService productDetailService;

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


    // Chuyển sang trang add product detail => Controller ProductDetail (view-add)
    @PostMapping("/next-add")
    public String nextAddProduct(@ModelAttribute Product product,
                                 HttpSession httpSession) {
        String randomKey = UUID.randomUUID().toString();
        httpSession.setAttribute("randomKey", randomKey);
        httpSession.setAttribute("productAdd" + randomKey, product);
        return "redirect:/nova/product-detail/view-add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name,
                      @RequestParam String description,
                      @RequestParam Integer materialId,
                      @RequestParam Integer categoryId,
                      @RequestParam Integer brandId,
                      @RequestParam Integer formId,
                      RedirectAttributes redirectAttributes) {
        productService.add(name, description, materialId, categoryId, brandId, formId);
        redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu thành công");
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
                         @RequestParam Integer materialId,
                         @RequestParam Integer categoryId,
                         @RequestParam Integer brandId,
                         @RequestParam Integer formId,
                         RedirectAttributes redirectAttributes) {
        productService.update(id, code, name, description, materialId, categoryId, brandId, formId);
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
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        if (productName.isEmpty() && description.isEmpty() && brandId == null && materialId == null && categoryId == null && formId == null) {
            return "redirect:/nova/product/page";
        }
        Page<ProductDto> pageProductDto = productService.search(materialId, brandId, formId, categoryId, productName, description, page);
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

        return "admin/product/product";
    }

    @PostMapping("/excel")
    public String importExcel(RedirectAttributes redirectAttributes, @RequestParam MultipartFile excelFile) throws IOException {
        if (productService.importExcelProduct(excelFile) == 1) {
            redirectAttributes.addFlashAttribute("mess", "Thêm dữ liệu excel thành công");
            return "redirect:/nova/product/page";
        } else if (productService.importExcelProduct(excelFile) == -1) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng kiểm tra lại dữ liệu trong file");
            return "redirect:/nova/product/page";
        } else {
            redirectAttributes.addFlashAttribute("error", "Đây không phải là file excel");
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

    @PostMapping("/restore/{id}")
    public String restore(@PathVariable Integer id,
                          RedirectAttributes redirectAttributes) {
        productService.restore(id);
        redirectAttributes.addFlashAttribute("mess", "Khôi phục dữ liệu thành công");
        return "redirect:/nova/product/view-restore";
    }

    @GetMapping("/search-restore")
    public String searchProductDelete(@RequestParam(required = false) String productName,
                         @RequestParam(required = false) Integer brandId,
                         @RequestParam(required = false) Integer materialId,
                         @RequestParam(required = false) Integer categoryId,
                         @RequestParam(required = false) Integer formId,
                         @RequestParam(required = false) String description,

                         @RequestParam(defaultValue = "0") int page,
                         Model model) {

        if (productName.isEmpty() && description.isEmpty() && brandId == null && materialId == null && categoryId == null && formId == null) {
            return "redirect:/nova/product/view-restore";
        }
        Page<ProductDto> pageProductDto = productService.searchProductDeleted(materialId, brandId, formId, categoryId, productName, description, page);
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
        return "admin/product/product-restore";
    }
}
