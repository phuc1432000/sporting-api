package com.sporting.api.controller;

import com.sporting.api.consts.ApiPath;
import com.sporting.api.consts.ErrorCode;
import com.sporting.api.consts.MessageConstant;
import com.sporting.api.dto.AccountDTO;
import com.sporting.api.response.AccountResponseDTO;
import com.sporting.api.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AccountController {

    @Autowired
    AccountService service;

    @GetMapping(value = ApiPath.ACCOUNT_GET_ALL)
    public ResponseEntity<AccountResponseDTO> getAll() {
        AccountResponseDTO response = new AccountResponseDTO();
        try {
            List<AccountDTO> list = service.findAll();
            response.setList(list);
            response.setMessage(MessageConstant.GET_ALL_SUCCESS + MessageConstant.ACCOUNT_API);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception ex) {
            log.error(MessageConstant.GET_ALL_FAIL + MessageConstant.ACCOUNT_API, ex);
            response.setMessage(MessageConstant.GET_ALL_FAIL + MessageConstant.ACCOUNT_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = ApiPath.ACCOUNT_GET_UUID)
    public ResponseEntity<AccountResponseDTO> getUUID(@RequestBody AccountDTO request) {
        AccountResponseDTO response = new AccountResponseDTO();
        try {
            if (null == request) {
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (null == request.getAccountId()) {
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.ACCOUNT_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            AccountDTO services = service.findByUUid(request.getAccountId());
            response.setData(services);
            response.setMessage(MessageConstant.GET_BY_ID_OK + MessageConstant.ACCOUNT_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(MessageConstant.GET_BY_ID_FAIL + MessageConstant.ACCOUNT_API, ex);
            response.setMessage(MessageConstant.GET_BY_ID_FAIL + MessageConstant.ACCOUNT_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.ACCOUNT_PERFORM_LOCK)
    public ResponseEntity<AccountResponseDTO> performLock(@RequestBody AccountDTO request) {
        AccountResponseDTO response = new AccountResponseDTO();
        try {
            if (null == request) {
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getAccountId())) {
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.ACCOUNT_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.performLock(request.getAccountId());
            if (!delete) {
                response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.UPDATE_STATUS_FAILURE);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.setMessage(MessageConstant.SUCCESS_PERFORM_LOCK_UNLOCK + MessageConstant.ACCOUNT_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(MessageConstant.FAIL_PERFORM_LOCK_UNLOCK + MessageConstant.ACCOUNT_API, ex);
            response.setMessage(MessageConstant.FAIL_PERFORM_LOCK_UNLOCK + MessageConstant.ACCOUNT_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.ACCOUNT_DELETE)
    public ResponseEntity<AccountResponseDTO> deleteAccount(@RequestBody AccountDTO request) {
        AccountResponseDTO response = new AccountResponseDTO();
        try {
            if (null == request) {
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getAccountId())) {
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.ACCOUNT_API);

                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.delete(request.getAccountId());
            if (!delete) {
                response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.DELETE_FAIL);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.SUCCESS_WHEN_DELETE);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(MessageConstant.ACCOUNT_API + MessageConstant.ERROR_WHEN_DELETE, ex);
            response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.ERROR_WHEN_DELETE + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.ACCOUNT_UPDATE)
    public ResponseEntity<AccountResponseDTO> updateAccount(@RequestBody AccountDTO request) {
        AccountResponseDTO response = new AccountResponseDTO();
        try {
            if (null == request) {
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            AccountDTO checkExist = service.findByUsername(request);
            if (null != checkExist) {
                response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.NON_EXISTED);// sai
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean update = service.update(request);
            if (!update) {
                response.setMessage(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.ACCOUNT_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.setMessage(MessageConstant.SUCCESS_WHEN_UPDATE + MessageConstant.ACCOUNT_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.ACCOUNT_API, ex);
            response.setMessage(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.ACCOUNT_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.ACCOUNT_CREATE)
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountDTO request) {
        AccountResponseDTO response = new AccountResponseDTO();
        try {
            if (null == request) {
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            AccountDTO checkExist = service.findByUsername(request);
            if (null != checkExist) {
                response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.NON_EXISTED);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean create = service.create(request);
            if (!create) {
                response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.CREATE_FAILURE);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.CREATE_SUCCESS);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(MessageConstant.ACCOUNT_API + MessageConstant.CREATE_FAILURE, ex);
            response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.CREATE_FAILURE + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.ACCOUNT_VALIDATE_LOGIN)
    public ResponseEntity<AccountResponseDTO> checkLogin(@RequestBody AccountDTO request) {
        AccountResponseDTO response = new AccountResponseDTO();
        try {
            if (null == request) {
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (null == request.getEmail()) {
                response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.NON_EXISTED);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            AccountDTO services = service.findByUsername(request);
            response.setData(services);
            response.setErrorCode(ErrorCode.SUCCESS);
            response.setMessage(MessageConstant.ACCOUNT_API + MessageConstant.ERROR_CODE_SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(MessageConstant.ERROR_WHEN_CHECK_LOGIN, ex);
            response.setMessage(MessageConstant.ERROR_WHEN_CHECK_LOGIN + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

