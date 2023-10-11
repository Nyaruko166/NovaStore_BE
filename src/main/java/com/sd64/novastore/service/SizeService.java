package com.sd64.novastore.service;

import com.sd64.novastore.model.Size;
import com.sd64.novastore.request.SizeRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SizeService {

    List<Size> getAll();

    Page<Size> getPage(int page);

    Size add(SizeRequest sizeRequest);

    Size update(SizeRequest sizeRequest, Integer id);

    Boolean delete(Integer id);

    Page<Size> search(String name, int page);
}
