package com.sporting.api.mapper;

import com.sporting.api.dto.OrderDetailDTO;
import com.sporting.api.entity.OrderDetail;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailMapper extends AbstractMapper<OrderDetail, OrderDetailDTO> {
    public OrderDetailMapper() {
        super(OrderDetail.class, OrderDetailDTO.class);
    }
}
