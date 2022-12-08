package com.sporting.api.service;

import com.sporting.api.dto.AccountDTO;

public interface AccountService extends BaseService<AccountDTO> {

    AccountDTO findByUsername(AccountDTO account);
}
