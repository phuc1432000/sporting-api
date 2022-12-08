package com.sporting.api.service;

import com.sporting.api.dto.ProductDTO;
import com.sporting.api.entity.Product;

import java.util.List;

public interface ProductService extends BaseService<ProductDTO> {
    List<ProductDTO> findByCategoryId (String categoryId);
}
