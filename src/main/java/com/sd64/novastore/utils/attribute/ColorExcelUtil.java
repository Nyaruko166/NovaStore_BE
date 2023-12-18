package com.sd64.novastore.utils.attribute;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.repository.ColorRepository;
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
public class ColorExcelUtil {

    @Autowired
    private ColorRepository colorRepository;

    public Boolean isValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public String generateCode() {
        Color colorFinalPresent = colorRepository.findTopByOrderByIdDesc();
        if (colorFinalPresent == null) {
            return "M0001";
        }
        Integer idFinalPresent = colorFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "M"+code;
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
                Color color = new Color();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            Color color1 = colorRepository.findByNameAndStatus(cell.getStringCellValue(), 1);
                            if (color1 != null) {
                                workbook.close();
                                fileInputStream.close();
                                File file = new File(path);
                                file.delete();
                                return "Tồn tại";
                            }
                            color.setName(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                setName.add(color.getName());
                listName.add(color.getName());
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
            List<Color> listColor = new ArrayList<>();
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
                    Color color = new Color();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cellIndex) {
                            case 0:
                                Color color1 = colorRepository.findByNameAndStatus(cell.getStringCellValue(), 1);
                                if (color1 != null) {
                                    workbook.close();
                                    fileInputStream.close();
                                    File file = new File(path);
                                    file.delete();
                                    return "Trùng tên";
                                }
                                color.setName(cell.getStringCellValue());
                                break;
                            default:
                                break;
                        }
                        cellIndex++;
                    }
                    color.setStatus(1);
                    color.setCreateDate(new Date());
                    color.setUpdateDate(new Date());
                    color.setCode(generateCode());
                    colorRepository.save(color);
                    listColor.add(color);
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
            colorRepository.saveAll(listColor);
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
