package com.sd64.novastore.service;

import com.sd64.novastore.model.Form;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FormService {
    List<Form> getAll();

    Page<Form> getPage(Integer page);

    Form add(Form form);

    Form update(Form form, Integer id);

    Form delete(Integer id);

    Page<Form> search(String name, int page);

    Form detail(Integer id);
}
