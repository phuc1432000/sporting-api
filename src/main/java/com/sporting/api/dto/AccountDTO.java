package com.sporting.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class AccountDTO implements Serializable {
    private Long id;
    private String accountId;
    private String email;
    private String password;
    private String fullName;
    private String role;
    private String phone;
    private String address;
    private String active;
}
