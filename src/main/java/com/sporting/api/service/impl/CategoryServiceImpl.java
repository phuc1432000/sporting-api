package com.sporting.api.service.impl;

import com.sporting.api.consts.StatusConstant;
import com.sporting.api.dto.CategoryDTO;
import com.sporting.api.entity.Category;
import com.sporting.api.mapper.CategoryMapper;
import com.sporting.api.repository.CategoryRepository;
import com.sporting.api.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper mapper;

    @Autowired
    CategoryRepository repository;

    /**
     * @param categoryDTO
     * @return
     */
    @Override
    public boolean create(CategoryDTO categoryDTO) {
        try {
            Category entity = mapper.convertDTOToEntity(categoryDTO);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            log.error("Error when create category:", e);
        }
        return false;
    }

    /**
     * @param categoryDTO
     * @return
     */
    @Override
    public boolean update(CategoryDTO categoryDTO) {
        try {
            Category entity = repository.findByCategoryId(categoryDTO.getCategoryId());
            if (null != entity) {
                Category newEntity = mapper.convertDTOToEntity(categoryDTO);
                repository.save(newEntity);
                return true;
            }
        } catch (Exception e) {
            log.error("Error when update category:", e);
        }
        return false;
    }

    /**
     * @param uuid
     * @return
     */
    @Override
    public CategoryDTO findByUUid(String uuid) {
        Category entity = repository.findByCategoryId(uuid);
        return entity == null ? null : mapper.convertEntityToDTO(entity);
    }

    /**
     * @return
     */
    @Override
    public List<CategoryDTO> findAll() {
        List<Category> entities = repository.findAll();
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
            Category entity = repository.findByCategoryId(uuid);
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
     * @param categoryId
     * @return
     */
    @Override
    public boolean performLock(String categoryId) {
        Category entity = repository.findByCategoryId(categoryId);
        if (!entity.getActive().equals(StatusConstant.STOPPED)) {
            entity.setActive((entity.getActive().equals(StatusConstant.ACTIVE)) ? StatusConstant.INACTIVE : StatusConstant.ACTIVE);
        }
        return repository.save(entity) != null;
    }
}
