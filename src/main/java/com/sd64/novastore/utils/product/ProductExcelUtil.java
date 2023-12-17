package com.sd64.novastore.utils.product;


import com.sd64.novastore.model.*;
import com.sd64.novastore.repository.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    @Autowired
    private ProductRepository productRepository;

    public Boolean isValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public String getProductFromExcel(String path) throws IOException {
        List<Product> listProduct = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet = workbook.getSheetAt(0);

        int rowIndex = 0;
        try {
            for (Row row : xssfSheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Product product = new Product();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
//                        case 0:
//                            Optional<Product> optionalProduct = productRepository.findAllByCode(cell.getStringCellValue());
//                            if (optionalProduct.isPresent()) {
//                                workbook.close();
//                                fileInputStream.close();
//                                File file = new File(path);
//                                file.delete();
//                                return "Trùng mã";
//                            }
//                            product.setCode(cell.getStringCellValue());
//                            break;
                        case 0:
                            product.setName(cell.getStringCellValue());
                            break;
                        case 1:
                            product.setDescription(cell.getStringCellValue());
                            break;
                        case 2:
                            product.setBrand(brandRepository.findByNameAndStatus(cell.getStringCellValue(), 1));
                            break;
                        case 3:
                            product.setMaterial(materialRepository.findByNameAndStatus(cell.getStringCellValue(), 1));
                            break;
                        case 4:
                            product.setCategory(categoryRepository.findByNameAndStatus(cell.getStringCellValue(), 1));
                            break;
                        case 5:
                            product.setForm(formRepository.findByNameAndStatus(cell.getStringCellValue(), 1));
                            break;
                        default:
                            break;
//                        case 3 -> price = BigDecimal.valueOf(cell.getNumericCellValue());
//                        case 4 -> brand = brandRepository.findByName(cell.getStringCellValue());
//                        case 5 -> material = materialRepository.findByName(cell.getStringCellValue());
//                        case 6 -> category = categoryRepository.findByName(cell.getStringCellValue());
//                        case 7 -> form = formRepository.findByName(cell.getStringCellValue());
//                        default -> {
//                        }
                    }
                    cellIndex++;
                }
                product.setStatus(1);
                product.setCreateDate(new Date());
                product.setUpdateDate(new Date());
                productRepository.save(product);
                product.setCode("SP"+product.getId());
                productRepository.save(product);
                listProduct.add(product);
            }
        } catch (Exception e) {
            workbook.close();
            fileInputStream.close();
            File file = new File(path);
            file.delete();
            return "Sai dữ liệu";
        }
        workbook.close();
        fileInputStream.close();
        File file = new File(path);
        file.delete();
        productRepository.saveAll(listProduct);
        return "okela";
    }
}
