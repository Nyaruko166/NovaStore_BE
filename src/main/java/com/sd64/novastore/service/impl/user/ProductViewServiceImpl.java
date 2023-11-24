package com.sd64.novastore.service.impl.user;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.repository.user.ProductViewRepository;
import com.sd64.novastore.service.user.ProductViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductViewServiceImpl implements ProductViewService {
    @Autowired
    private ProductViewRepository productViewRepository;

    @Override
    public List<Product> getAllProductView() {
        return productViewRepository.getAllProductView();
    }

    @Override
    public List<Product> getAllProductResponse () {
        return productViewRepository.getAllProductResponse();
    }

    @Override
    public Product getOne(Integer id) {
        Optional<Product> optional = productViewRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }
}
