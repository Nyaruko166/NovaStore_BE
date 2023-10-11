package com.sd64.novastore.service.impl;

import com.sd64.novastore.request.FormRequest;
import com.sd64.novastore.model.Form;
import com.sd64.novastore.repository.FormRepository;
import com.sd64.novastore.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormServiceImpl implements FormService {
    @Autowired
    private FormRepository formRepository;

    @Override
    public List<Form> getAll(){
        return  formRepository.findAllByStatus(1);
    }

    @Override
    public Page<Form> getAll(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return formRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public Form add(FormRequest formRequest) {
        Form form = formRequest.map(new Form());
        return formRepository.save(form);
    }

    @Override
    public Form update(FormRequest formRequest, Integer id) {
        Optional<Form> optional = formRepository.findById(id);
        Form color = formRequest.map(optional.get());
        return formRepository.save(color);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<Form> optional = formRepository.findById(id);
        if (optional.isPresent()){
            Form form = optional.get();
            form.setStatus(0);
            formRepository.save(form);
            return true;
        } else {
            return false;
        }
    }
}
