package com.sd64.novastore.utils.product;

import com.sd64.novastore.model.Material;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.repository.MaterialRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class ExcelUtil {

    @Autowired
    private MaterialRepository materialRepository;

    public Boolean isValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public String generateProductCode(Integer id) {
        String code = String.format("%04d", id);
        return "CL"+code;
    }

    public String getFromExcel(String path) throws IOException {
        List<Material> listMaterial = new ArrayList<>();
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
                Material material = new Material();
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
                            Material material1 = materialRepository.findByName(cell.getStringCellValue());
                            if (material1 != null) {
                                workbook.close();
                                fileInputStream.close();
                                File file = new File(path);
                                file.delete();
                                return "Trùng tên";
                            }
                            material.setName(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                material.setStatus(1);
                material.setCreateDate(new Date());
                material.setUpdateDate(new Date());
                materialRepository.save(material);
                material.setCode(generateProductCode(material.getId()));
                materialRepository.save(material);
                listMaterial.add(material);
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
        materialRepository.saveAll(listMaterial);
        return "okela";
    }
}
