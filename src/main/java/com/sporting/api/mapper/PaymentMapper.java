package com.sporting.api.mapper;

import com.sporting.api.dto.PaymentDTO;
import com.sporting.api.entity.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper extends AbstractMapper<Payment, PaymentDTO> {
    public PaymentMapper() {
        super(Payment.class, PaymentDTO.class);
    }
}
