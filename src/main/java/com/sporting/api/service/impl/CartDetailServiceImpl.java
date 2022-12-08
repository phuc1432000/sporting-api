package com.sporting.api.service.impl;

import com.sporting.api.consts.StatusConstant;
import com.sporting.api.dto.CartDetailDTO;
import com.sporting.api.entity.CartDetail;
import com.sporting.api.mapper.CartDetailMapper;
import com.sporting.api.repository.CartDetailRepository;
import com.sporting.api.service.CartDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    CartDetailMapper mapper;

    @Autowired
    CartDetailRepository repository;

    /**
     * @param cartDetailDTO
     * @return
     */
    @Override
    public boolean create(CartDetailDTO cartDetailDTO) {
        try {
            CartDetail entity = mapper.convertDTOToEntity(cartDetailDTO);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            log.error("Error when create cart detail:", e);
        }
        return false;
    }

    /**
     * @param cartDetailDTO
     * @return
     */
    @Override
    public boolean update(CartDetailDTO cartDetailDTO) {
        try {
            CartDetail entity = repository.findByCartId(cartDetailDTO.getCartId());
            if (null != entity) {
                CartDetail newEntity = mapper.convertDTOToEntity(cartDetailDTO);
                repository.save(newEntity);
                return true;
            }
        } catch (Exception e) {
            log.error("Error when update cart detail:", e);
        }
        return false;
    }

    /**
     * @param uuid
     * @return
     */
    @Override
    public CartDetailDTO findByUUid(String uuid) {
        CartDetail entity = repository.findByCartId(uuid);
        return entity == null ? null : mapper.convertEntityToDTO(entity);
    }

    /**
     * @return
     */
    @Override
    public List<CartDetailDTO> findAll() {
        List<CartDetail> entities = repository.findAll();
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
            CartDetail entity = repository.findByCartId(uuid);
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
        CartDetail entity = repository.findByCartId(uuid);
        if (!entity.getActive().equals(StatusConstant.STOPPED)) {
            entity.setActive((entity.getActive().equals(StatusConstant.ACTIVE)) ? StatusConstant.INACTIVE : StatusConstant.ACTIVE);
        }
        return repository.save(entity) != null;
    }
}
