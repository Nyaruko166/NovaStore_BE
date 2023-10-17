package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.request.FormRequest;
import com.sd64.novastore.model.Form;
import com.sd64.novastore.repository.FormRepository;
import com.sd64.novastore.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FormServiceImpl implements FormService {
    @Autowired
    private FormRepository formRepository;

    @Override
    public List<Form> getAll(){
        return formRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Form> getPage(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return formRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    @Override
    public Form add(Form form) {
        form.setStatus(1);
        form.setCreateDate(new java.util.Date());
        form.setUpdateDate(new java.util.Date());
        return formRepository.save(form);
    }

    @Override
    public Form update(Form form, Integer id) {
        Optional<Form> optional = formRepository.findById(id);
        if (optional.isPresent()) {
            Form updateForm = optional.get();
            form.setId(id);
            form.setName(updateForm.getName());
            form.setStatus(updateForm.getStatus());
            form.setCreateDate(updateForm.getCreateDate());
            form.setUpdateDate(new Date());
            return formRepository.save(form);
        } else {
            return null;
        }
    }

    @Override
    public Form delete(Integer id) {
        Optional<Form> optional = formRepository.findById(id);
        if (optional.isPresent()) {
            Form form = optional.get();
            form.setStatus(0);
            return formRepository.save(form);
        } else {
            return null;
        }
    }

    @Override
    public Page<Form> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return formRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
