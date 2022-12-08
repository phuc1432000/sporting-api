package com.sporting.api.repository;

import com.sporting.api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);

    Account findByAccountId(String accountId);
}
