package com.sd64.novastore.controller.admin;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/template")
public class ExcelTemplate {

    @GetMapping("/material")
    public ResponseEntity<?> download() throws FileNotFoundException {
        String path = "D:\\Study\\FPTPolytechnic\\2023\\FALL_2023\\PRO2111_DuAnTotNghiep\\NoveStore\\materialTemplate.xlsx";
        File file = new File(path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
