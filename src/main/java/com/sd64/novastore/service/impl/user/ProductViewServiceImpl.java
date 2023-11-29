package com.sd64.novastore.service.impl.user;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.dto.common.ProductResponseHomeDto;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.repository.ProductDetailRepository;
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

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public List<Product> getAllProductView() {
        return productViewRepository.getAllProductView();
    }

//    @Override
//    public List<Product> getAllProductResponse () {
//        return productViewRepository.getAllProductResponse();
//    }

    @Override
    public Product getOne(Integer id) {
        Optional<Product> optional = productViewRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public ProductResponseHomeDto getProductResponse() {
        return productViewRepository.getAllProductResponseHome().get(0);
    }

    @Override
    public List<ProductResponseHomeDto> getAllProductResponse() {
        return productViewRepository.getAllProductResponseHome();
    }

}
