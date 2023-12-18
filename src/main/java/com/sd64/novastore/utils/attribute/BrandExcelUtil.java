package com.sd64.novastore.utils.attribute;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.repository.BrandRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
public class BrandExcelUtil {

    @Autowired
    private BrandRepository brandRepository;

    public Boolean isValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public String generateCode() {
        Brand brandFinalPresent = brandRepository.findTopByOrderByIdDesc();
        if (brandFinalPresent == null) {
            return "TH00001";
        }
        Integer idFinalPresent = brandFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "TH"+code;
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
                Brand brand = new Brand();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            Brand brand1 = brandRepository.findByNameAndStatus(cell.getStringCellValue(), 1);
                            if (brand1 != null) {
                                workbook.close();
                                fileInputStream.close();
                                File file = new File(path);
                                file.delete();
                                return "Tồn tại";
                            }
                            brand.setName(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                setName.add(brand.getName());
                listName.add(brand.getName());
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
        if (listName.isEmpty() && setName.isEmpty()) {
            return "Trống";
        }
        workbook.close();
        fileInputStream.close();
        return "Oke";
    }

    public String getFromExcel(String path) throws IOException {
        String result = checkDataFromExcel(path);
        if (result.contains("Oke")) {
            List<Brand> listBrand = new ArrayList<>();
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
                    Brand brand = new Brand();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cellIndex) {
                            case 0:
                                Brand brand1 = brandRepository.findByNameAndStatus(cell.getStringCellValue(), 1);
                                if (brand1 != null) {
                                    workbook.close();
                                    fileInputStream.close();
                                    File file = new File(path);
                                    file.delete();
                                    return "Trùng tên";
                                }
                                brand.setName(cell.getStringCellValue());
                                break;
                            default:
                                break;
                        }
                        cellIndex++;
                    }
                    brand.setStatus(1);
                    brand.setCreateDate(new Date());
                    brand.setUpdateDate(new Date());
                    brand.setCode(generateCode());
                    brandRepository.save(brand);
                    listBrand.add(brand);
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
            brandRepository.saveAll(listBrand);
            return "okela";
        } else if (result.contains("Tồn tại")) {
            return "Tồn tại";
        } else if (result.contains("Sai dữ liệu")) {
            return "Sai dữ liệu";
        } else if (result.contains("Trùng")) {
            return "Trùng";
        } else if (result.contains("Trống")) {
            return "Trống";
        }
        return null;
    }
}
