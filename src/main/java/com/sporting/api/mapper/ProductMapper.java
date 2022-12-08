package com.sporting.api.mapper;

import com.sporting.api.dto.ProductDTO;
import com.sporting.api.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper extends AbstractMapper<Product, ProductDTO> {
    public ProductMapper() {
        super(Product.class, ProductDTO.class);
    }
}
