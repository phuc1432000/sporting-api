package com.sporting.api.service.impl;

import com.sporting.api.consts.StatusConstant;
import com.sporting.api.dto.ProductDTO;
import com.sporting.api.entity.Product;
import com.sporting.api.mapper.ProductMapper;
import com.sporting.api.repository.ProductRepository;
import com.sporting.api.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper mapper;

    @Autowired
    ProductRepository repository;

    /**
     * @param productDTO
     * @return
     */
    @Override
    public boolean create(ProductDTO productDTO) {
        try {
            Product entity = mapper.convertDTOToEntity(productDTO);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            log.error("Error when create Product:", e);
        }
        return false;
    }

    /**
     * @param productDTO
     * @return
     */
    @Override
    public boolean update(ProductDTO productDTO) {
        try {
            Product entity = repository.findByProductId(productDTO.getProductId());
            if (null != entity) {
                Product newEntity = mapper.convertDTOToEntity(productDTO);
                repository.save(newEntity);
                return true;
            }
        } catch (Exception e) {
            log.error("Error when update Product:", e);
        }
        return false;
    }

    /**
     * @param uuid
     * @return
     */
    @Override
    public ProductDTO findByUUid(String uuid) {
        Product entity = repository.findByProductId(uuid);
        return entity == null ? null : mapper.convertEntityToDTO(entity);
    }

    /**
     * @return
     */
    @Override
    public List<ProductDTO> findAll() {
        List<Product> entities = repository.findAll();
        return entities == null || entities.size() == 0 ?
                new ArrayList<>() :
                entities.stream().map(obj -> mapper.convertEntityToDTO(obj))
                        .filter(obj -> !obj.getActive().equals(StatusConstant.STOPPED)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findByCategoryId(String categoryId) {
        List<Product> entities = repository.findByCategoryId(categoryId);
        return entities == null || entities.size() == 0 ?
                new ArrayList<>() :
                entities.stream().map(obj -> mapper.convertEntityToDTO(obj))
                        .filter(obj -> !obj.getActive().equals(StatusConstant.STOPPED)).collect(Collectors.toList());
    }

    /**
     * @param uuid
     * @return
     */
    @Override
    public boolean delete(String uuid) {
        try {
            Product entity = repository.findByProductId(uuid);
            if (entity != null) {
                entity.setActive(StatusConstant.STOPPED);
                return repository.save(entity) != null;
            }
        } catch (Exception e) {
            log.error("Error when delete:", e);
        }
        return false;
    }

    /**
     * @param uuid
     * @return
     */
    @Override
    public boolean performLock(String uuid) {
        Product entity = repository.findByProductId(uuid);
        if (!entity.getActive().equals(StatusConstant.STOPPED)) {
            entity.setActive((entity.getActive().equals(StatusConstant.ACTIVE)) ? StatusConstant.INACTIVE : StatusConstant.ACTIVE);
        }
        return repository.save(entity) != null;
    }
}
