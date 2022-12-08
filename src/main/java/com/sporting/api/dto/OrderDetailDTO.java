package com.sporting.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class OrderDetailDTO implements Serializable {
    private long id;
    private String orderId;
    private String accountId;
    private String productId;
    private String active;
}
