package com.sd64.novastore.service;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Form;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FormService {
    List<Form> getAll();

    Page<Form> getPage(Integer page);

    Boolean add(String name);

    Boolean update(Form form, Integer id);

    Form delete(Integer id);

    Page<Form> search(String name, int page);

    Form detail(Integer id);

    Page<Form> getAllDeleted(int page);

    Form restore(Integer id);

    Page<Form> searchDelete(String name, int page);

    Integer importExcel(MultipartFile file) throws IOException;
}
