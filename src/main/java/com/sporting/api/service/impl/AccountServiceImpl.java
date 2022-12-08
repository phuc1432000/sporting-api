package com.sporting.api.service.impl;

import com.sporting.api.consts.StatusConstant;
import com.sporting.api.dto.AccountDTO;
import com.sporting.api.entity.Account;
import com.sporting.api.mapper.AccountMapper;
import com.sporting.api.repository.AccountRepository;
import com.sporting.api.service.AccountService;
import com.sporting.api.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository repository;

    @Autowired
    AccountMapper mapper;

    @Override
    public boolean create(AccountDTO accountDTO) {
        try {
            Account entity = mapper.convertDTOToEntity(accountDTO);
            entity.setPassword(Md5Utils.MD5(entity.getPassword()));
            repository.save(entity);
            return true;
        } catch (Exception e) {
            log.error("Error when create account:", e);
        }
        return false;
    }

    @Override
    public boolean update(AccountDTO dto) {
        try {
            Account entity = repository.findByAccountId(dto.getAccountId());
            if (null != entity) {
                Account newEntity = mapper.convertDTOToEntity(dto);
                newEntity.setPassword(dto.getPassword().equals("") ? entity.getPassword() : Md5Utils.MD5(dto.getPassword()));
                repository.save(newEntity);
                return true;
            }
        } catch (Exception e) {
            log.error("Error when update Account:", e);
        }
        return false;
    }

    @Override
    public AccountDTO findByUUid(String accountId) {
        Account entity = repository.findByAccountId(accountId);
        return entity == null ? null : mapper.convertEntityToDTO(entity);
    }

    @Override
    public List<AccountDTO> findAll() {
        List<Account> entities = repository.findAll();
        return entities == null || entities.size() == 0 ?
                new ArrayList<>() : entities.stream().map(obj -> mapper.convertEntityToDTO(obj))
                .filter(obj -> !obj.getActive().equals(StatusConstant.STOPPED)).collect(Collectors.toList());
    }

    @Override
    public boolean delete(String accountId) {
        try {
            Account account = repository.findByAccountId(accountId);
            if (account != null) {
                account.setActive(StatusConstant.STOPPED);
                return repository.save(account) != null;
            }
        } catch (Exception e) {
            log.error("Error when delete:", e);
        }
        return false;
    }

    @Override
    public boolean performLock(String accountId) {
        Account account = repository.findByAccountId(accountId);
        if (!account.getActive().equals(StatusConstant.STOPPED)) {
            account.setActive((account.getActive().equals(StatusConstant.ACTIVE)) ? StatusConstant.INACTIVE : StatusConstant.ACTIVE);
        }
        return repository.save(account) != null;
    }

    @Override
    public AccountDTO findByUsername(AccountDTO account) {
        Account entity = repository.findByEmail(account.getEmail());
        try {
            if (entity != null) {
                return Md5Utils.checkLogin(entity.getPassword(), account.getPassword()) ? mapper.convertEntityToDTO(entity) : null;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}