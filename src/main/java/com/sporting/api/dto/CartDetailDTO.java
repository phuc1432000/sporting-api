package com.sporting.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class CartDetailDTO implements Serializable {
    private long id;
    private String cartId;
    private String accountId;
    private String productId;
    private int count;
    private String active;
}
