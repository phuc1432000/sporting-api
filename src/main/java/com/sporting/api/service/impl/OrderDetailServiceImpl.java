package com.sporting.api.service.impl;

import com.sporting.api.consts.StatusConstant;
import com.sporting.api.dto.OrderDetailDTO;
import com.sporting.api.entity.OrderDetail;
import com.sporting.api.mapper.OrderDetailMapper;
import com.sporting.api.repository.OrderDetailRepository;
import com.sporting.api.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderDetailMapper mapper;

    @Autowired
    OrderDetailRepository repository;

    /**
     * @param orderDetailDTO
     * @return
     */
    @Override
    public boolean create(OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail entity = mapper.convertDTOToEntity(orderDetailDTO);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            log.error("Error when create Order Detail:", e);
        }
        return false;
    }

    /**
     * @param orderDetailDTO
     * @return
     */
    @Override
    public boolean update(OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail entity = repository.findByOrderId(orderDetailDTO.getOrderId());
            if (null != entity) {
                OrderDetail newEntity = mapper.convertDTOToEntity(orderDetailDTO);
                repository.save(newEntity);
                return true;
            }
        } catch (Exception e) {
            log.error("Error when update Order Detail:", e);
        }
        return false;
    }

    /**
     * @param uuid
     * @return
     */
    @Override
    public OrderDetailDTO findByUUid(String uuid) {
        OrderDetail entity = repository.findByOrderId(uuid);
        return entity == null ? null : mapper.convertEntityToDTO(entity);
    }

    /**
     * @return
     */
    @Override
    public List<OrderDetailDTO> findAll() {
        List<OrderDetail> entities = repository.findAll();
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
            OrderDetail entity = repository.findByOrderId(uuid);
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
        OrderDetail entity = repository.findByOrderId(uuid);
        if (!entity.getActive().equals(StatusConstant.STOPPED)) {
            entity.setActive((entity.getActive().equals(StatusConstant.ACTIVE)) ? StatusConstant.INACTIVE : StatusConstant.ACTIVE);
        }
        return repository.save(entity) != null;
    }
}
