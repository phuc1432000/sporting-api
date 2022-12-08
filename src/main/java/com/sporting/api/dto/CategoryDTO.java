package com.sporting.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class CategoryDTO implements Serializable {
    private long id;
    private String categoryId;
    private String name;
    private String description;
    private String active;
}
