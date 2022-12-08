package com.sporting.api.service.impl;

import com.sporting.api.consts.StatusConstant;
import com.sporting.api.dto.PaymentDTO;
import com.sporting.api.entity.Payment;
import com.sporting.api.mapper.PaymentMapper;
import com.sporting.api.repository.PaymentRepository;
import com.sporting.api.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository repository;

    @Autowired
    PaymentMapper mapper;

    /**
     * @param paymentDTO
     * @return
     */
    @Override
    public boolean create(PaymentDTO paymentDTO) {
        try {
            Payment entity = mapper.convertDTOToEntity(paymentDTO);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            log.error("Error when create Payment:", e);
        }
        return false;
    }

    /**
     * @param paymentDTO
     * @return
     */
    @Override
    public boolean update(PaymentDTO paymentDTO) {
        try {
            Payment entity = repository.findByPaymentId(paymentDTO.getPaymentId());
            if (null != entity) {
                Payment newEntity = mapper.convertDTOToEntity(paymentDTO);
                repository.save(newEntity);
                return true;
            }
        } catch (Exception e) {
            log.error("Error when update payment:", e);
        }
        return false;
    }

    /**
     * @param uuid
     * @return
     */
    @Override
    public PaymentDTO findByUUid(String uuid) {
        Payment entity = repository.findByPaymentId(uuid);
        return entity == null ? null : mapper.convertEntityToDTO(entity);
    }

    /**
     * @return
     */
    @Override
    public List<PaymentDTO> findAll() {
        List<Payment> entities = repository.findAll();
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
            Payment entity = repository.findByPaymentId(uuid);
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
        Payment entity = repository.findByPaymentId(uuid);
        if (!entity.getActive().equals(StatusConstant.STOPPED)) {
            entity.setActive((entity.getActive().equals(StatusConstant.ACTIVE)) ? StatusConstant.INACTIVE : StatusConstant.ACTIVE);
        }
        return repository.save(entity) != null;
    }
}
