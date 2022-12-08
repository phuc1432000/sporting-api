package com.sporting.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class PaymentDTO implements Serializable {
    private long id;
    private String paymentId;
    private String paymentAmount;
    private String type;
    private String active;
}
