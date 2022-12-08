package com.sporting.api.mapper;

import com.sporting.api.dto.AccountDTO;
import com.sporting.api.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper extends AbstractMapper<Account, AccountDTO> {
    public AccountMapper() {
        super(Account.class, AccountDTO.class);
    }
}
