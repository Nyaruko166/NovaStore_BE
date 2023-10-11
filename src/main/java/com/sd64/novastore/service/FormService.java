package com.sd64.novastore.service;

import com.sd64.novastore.request.FormRequest;
import com.sd64.novastore.model.Form;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FormService {
    List<Form> getAll();

    Page<Form> getAll(Integer page);

    Form add(FormRequest formRequest);

    Form update(FormRequest formRequest, Integer id);

    Boolean delete(Integer id);
}
