package com.sd64.novastore.utils.attribute;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.model.Category;
import com.sd64.novastore.repository.CategoryRepository;
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
public class CategoryExcelUtil {


    @Autowired
    private CategoryRepository categoryRepository;

    public Boolean isValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public String generateCode() {
        Category categoryFinalPresent = categoryRepository.findTopByOrderByIdDesc();
        if (categoryFinalPresent == null) {
            return "L00001";
        }
        Integer idFinalPresent = categoryFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "L"+code;
    }

    public String checkDataFromExcel(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet = workbook.getSheetAt(0);
        Set<String> setName = new HashSet<>();
        List<String> listName = new ArrayList<>();
        int rowIndex = 0;
        try {
            for (Row row : xssfSheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Category category = new Category();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            Category category1 = categoryRepository.findByNameAndStatus(cell.getStringCellValue(), 1);
                            if (category1 != null) {
                                workbook.close();
                                fileInputStream.close();
                                File file = new File(path);
                                file.delete();
                                return "Tồn tại";
                            }
                            category.setName(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                setName.add(category.getName());
                listName.add(category.getName());
            }
        } catch (Exception e) {
            workbook.close();
            fileInputStream.close();
            File file = new File(path);
            file.delete();
            return "Sai dữ liệu";
        }
        if (listName.size() != setName.size()) {
            workbook.close();
            fileInputStream.close();
            File file = new File(path);
            file.delete();
            return "Trùng";
        }
        workbook.close();
        fileInputStream.close();
        return "Oke";
    }

    public String getFromExcel(String path) throws IOException {
        String result = checkDataFromExcel(path);
        if (result.contains("Oke")) {
            List<Category> listCategory = new ArrayList<>();
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
                    Category category = new Category();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cellIndex) {
                            case 0:
                                Category category1 = categoryRepository.findByNameAndStatus(cell.getStringCellValue(), 1);
                                if (category1 != null) {
                                    workbook.close();
                                    fileInputStream.close();
                                    File file = new File(path);
                                    file.delete();
                                    return "Trùng tên";
                                }
                                category.setName(cell.getStringCellValue());
                                break;
                            default:
                                break;
                        }
                        cellIndex++;
                    }
                    category.setStatus(1);
                    category.setCreateDate(new Date());
                    category.setUpdateDate(new Date());
                    category.setCode(generateCode());
                    categoryRepository.save(category);
                    listCategory.add(category);
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
            categoryRepository.saveAll(listCategory);
            return "okela";
        } else if (result.contains("Tồn tại")) {
            return "Tồn tại";
        } else if (result.contains("Sai dữ liệu")) {
            return "Sai dữ liệu";
        } else if (result.contains("Trùng")) {
            return "Trùng";
        }
        return null;
    }
}
