package com.sporting.api.mapper;

import com.sporting.api.dto.CartDetailDTO;
import com.sporting.api.entity.CartDetail;
import org.springframework.stereotype.Service;

@Service
public class CartDetailMapper extends AbstractMapper<CartDetail, CartDetailDTO> {
    public CartDetailMapper() {
        super(CartDetail.class, CartDetailDTO.class);
    }
}
