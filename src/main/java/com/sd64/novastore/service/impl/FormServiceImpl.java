package com.sd64.novastore.service.impl;

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
        return formRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Form> getPage(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return formRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    private Boolean checkName(String name) {
        Form form = formRepository.findByName(name);
        if (form != null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean add(Form form) {
        if (checkName(form.getName())) {
            form.setStatus(1);
            form.setCreateDate(new java.util.Date());
            form.setUpdateDate(new java.util.Date());
            formRepository.save(form);
            form.setCode("K"+form.getId());
            formRepository.save(form);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean update(Form form, Integer id) {
        Optional<Form> optional = formRepository.findById(id);
        if (optional.isPresent() && checkName(form.getName())) {
            Form updateForm = optional.get();
            updateForm.setId(id);
            updateForm.setName(form.getName());
            updateForm.setUpdateDate(new Date());
            formRepository.save(updateForm);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Form delete(Integer id) {
        Optional<Form> optional = formRepository.findById(id);
        if (optional.isPresent()) {
            Form form = optional.get();
            form.setStatus(0);
            form.setUpdateDate(new Date());
            return formRepository.save(form);
        } else {
            return null;
        }
    }

    @Override
    public Page<Form> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return formRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 1, pageable);
    }

    @Override
    public Form detail(Integer id) {
        Optional<Form> optional = formRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<Form> getAllDeleted(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return formRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 0);
    }

    @Override
    public Form restore(Integer id) {
        Optional<Form> optionalForm = formRepository.findById(id);
        if (optionalForm.isPresent()) {
            Form restoreForm = optionalForm.get();
            restoreForm.setStatus(1);
            restoreForm.setUpdateDate(new Date());
            return formRepository.save(restoreForm);
        } else {
            return null;
        }
    }

    @Override
    public Page<Form> searchDelete(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return formRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 0, pageable);
    }
}
