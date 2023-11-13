package com.sd64.novastore.utils.product;


import com.sd64.novastore.model.*;
import com.sd64.novastore.repository.BrandRepository;
import com.sd64.novastore.repository.CategoryRepository;
import com.sd64.novastore.repository.FormRepository;
import com.sd64.novastore.repository.MaterialRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Component
public class ProductExcelUtil {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private FormRepository formRepository;

    public Boolean isValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<Product> getProductFromExcel(String path) throws IOException {
        String name = "";
        String description = "";
        BigDecimal price = null;
        Category category = new Category();
        Brand brand = new Brand();
        Material material = new Material();
        Form form = new Form();

        List<Product> listProduct = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet = workbook.getSheetAt(0);

        int rowIndex = 0;
        for (Row row : xssfSheet) {
            if (rowIndex == 0) {
                rowIndex++;
                continue;
            }
            Iterator<Cell> cellIterator = row.iterator();
            int cellIndex = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cellIndex) {
                    case 0 -> name = cell.getStringCellValue();
                    case 1 -> description = cell.getStringCellValue();
                    case 2 -> price = BigDecimal.valueOf(cell.getNumericCellValue());
                    case 3 -> brand = brandRepository.findByName(cell.getStringCellValue());
                    case 4 -> material = materialRepository.findByName(cell.getStringCellValue());
                    case 5 -> category = categoryRepository.findByName(cell.getStringCellValue());
                    case 6 -> form = formRepository.findByName(cell.getStringCellValue());
                    default -> {}
                }
                cellIndex++;
            }
            listProduct.add(new Product(null, name, description, price, new Date(), new Date(), 1, category, brand, material, form));
        }
        workbook.close();
        fileInputStream.close();
        File file = new File(path);
        file.delete();
        return listProduct;
    }
}
