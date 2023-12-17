package com.sd64.novastore.utils.attribute;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Form;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.repository.FormRepository;
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
public class FormExcelUtil {

    @Autowired
    private FormRepository formRepository;

    public Boolean isValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public String generateCode() {
        Form formFinalPresent = formRepository.findTopByOrderByIdDesc();
        if (formFinalPresent == null) {
            return "KD00001";
        }
        Integer idFinalPresent = formFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "KD"+code;
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
                Form form = new Form();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            Form form1 = formRepository.findByNameAndStatus(cell.getStringCellValue(), 1);
                            if (form1 != null) {
                                workbook.close();
                                fileInputStream.close();
                                File file = new File(path);
                                file.delete();
                                return "Tồn tại";
                            }
                            form.setName(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                setName.add(form.getName());
                listName.add(form.getName());
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
            List<Form> listForm = new ArrayList<>();
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
                    Form form = new Form();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cellIndex) {
                            case 0:
                                Form form1 = formRepository.findByNameAndStatus(cell.getStringCellValue(), 1);
                                if (form1 != null) {
                                    workbook.close();
                                    fileInputStream.close();
                                    File file = new File(path);
                                    file.delete();
                                    return "Trùng tên";
                                }
                                form.setName(cell.getStringCellValue());
                                break;
                            default:
                                break;
                        }
                        cellIndex++;
                    }
                    form.setStatus(1);
                    form.setCreateDate(new Date());
                    form.setUpdateDate(new Date());
                    form.setCode(generateCode());
                    formRepository.save(form);
                    listForm.add(form);
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
            formRepository.saveAll(listForm);
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
