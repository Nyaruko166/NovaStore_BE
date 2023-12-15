package com.sd64.novastore.utils.attribute;

import com.sd64.novastore.model.Material;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.SizeRepository;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Component
public class SizeExcelUtil {

    @Autowired
    private SizeRepository sizeRepository;

    public Boolean isValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public String generateCode() {
        Size sizeFinalPresent = sizeRepository.findTopByOrderByIdDesc();
        if (sizeFinalPresent == null) {
            return "S00001";
        }
        Integer idFinalPresent = sizeFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "S"+code;
    }

    public String checkDataFromExcel(String path) throws IOException {
        Set<String> setName = new HashSet<>();
        List<String> listName = new ArrayList<>();
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
                Size size = new Size();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                String sizeStr = String.valueOf(new Double(cell.getNumericCellValue()).intValue());
                                Size size1 = sizeRepository.findByName(sizeStr);
                                if (size1 != null) {
                                    workbook.close();
                                    fileInputStream.close();
                                    File file = new File(path);
                                    file.delete();
                                    return "Tồn tại";
                                }
                                size.setName(sizeStr);
                            } else {
                                Size size1 = sizeRepository.findByName(cell.getStringCellValue());
                                if (size1 != null) {
                                    workbook.close();
                                    fileInputStream.close();
                                    File file = new File(path);
                                    file.delete();
                                    return "Tồn tại";
                                }
                                size.setName(cell.getStringCellValue());
                            }
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                setName.add(size.getName());
                listName.add(size.getName());
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
        System.out.println();
        if (result.contains("Oke")) {
            List<Size> listSize = new ArrayList<>();
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
                    Size size = new Size();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cellIndex) {
                            case 0:
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    String sizeStr = String.valueOf(new Double(cell.getNumericCellValue()).intValue());
                                    Size size1 = sizeRepository.findByName(sizeStr);
                                    if (size1 != null) {
                                        workbook.close();
                                        fileInputStream.close();
                                        File file = new File(path);
                                        file.delete();
                                        return "Trùng tên";
                                    }
                                    size.setName(sizeStr);
                                } else {
                                    size.setName(cell.getStringCellValue());
                                }
                                break;
                            default:
                                break;
                        }
                        cellIndex++;
                    }
                    size.setStatus(1);
                    size.setCreateDate(new Date());
                    size.setUpdateDate(new Date());
                    size.setCode(generateCode());
                    sizeRepository.save(size);
                    listSize.add(size);
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
            sizeRepository.saveAll(listSize);
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
