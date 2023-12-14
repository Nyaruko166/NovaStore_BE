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

    public Integer checkDataFromExcel(String path) throws IOException {
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
                            Color color1 = colorRepository.findByName(cell.getStringCellValue());
                            if (color1 != null) {
                                workbook.close();
                                fileInputStream.close();
                                File file = new File(path);
                                file.delete();
                                return -1;
                            }
                            color.setName(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
            }
        } catch (Exception e) {
            workbook.close();
            fileInputStream.close();
            File file = new File(path);
            file.delete();
            return 0;
        }
        workbook.close();
        fileInputStream.close();
        return 1;
    }

    public String getFromExcel(String path) throws IOException {
        Integer result = checkDataFromExcel(path);
        if (result == 1) {
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
                                Color color1 = colorRepository.findByName(cell.getStringCellValue());
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
        } else if (result == -1) {
            return "Trùng tên";
        } else if (result== 0) {
            return "Sai dữ liệu";
        }
        return null;
    }
}
