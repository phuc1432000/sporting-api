package com.sporting.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class ProductDTO implements Serializable {
    private long id;
    private String productId;
    private String productName;
    private String productPrice;
    private String productDescription;
    private String productQuantity;
    private String categoryId;
    private String active;
    private String img;
}
