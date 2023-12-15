package com.sd64.novastore.service;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SizeService {

    List<Size> getAll();

    Page<Size> getPage(Integer page);

    Boolean add(String name);

    Boolean update(Integer id, String name);

    Size delete(Integer id);

    Page<Size> search(String name, int page);

    Size detail(Integer id);

    Page<Size> getAllSizeDelete(int page);

    Size restore(Integer id);

    Page<Size> searchDeleted(String name, int page);

    String importExcel(MultipartFile file) throws IOException;
}