package com.sporting.api.controller;

import com.sporting.api.consts.ApiPath;
import com.sporting.api.consts.ErrorCode;
import com.sporting.api.consts.MessageConstant;
import com.sporting.api.dto.CategoryDTO;
import com.sporting.api.response.AccountResponseDTO;
import com.sporting.api.response.CategoryResponseDTO;
import com.sporting.api.response.ProductResponseDTO;
import com.sporting.api.service.CategoryService;
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
public class CategoryController {
    @Autowired
    CategoryService service;

    @GetMapping(value = ApiPath.CATEGORY_GET_ALL)
    public ResponseEntity<CategoryResponseDTO> getAll() {
        CategoryResponseDTO response = new CategoryResponseDTO();
        try {
            List<CategoryDTO> list = service.findAll();
            response.setList(list);
            response.setMessage("Success when get all Category");
            response.setMessage(MessageConstant.GET_ALL_SUCCESS + MessageConstant.ACCOUNT_API);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception ex) {
            //log.error("Error when get all Category:", ex);
            log.error(MessageConstant.GET_ALL_FAIL + MessageConstant.CATEGORY_API, ex);
            //response.setMessage("Error when get all Category: " + ex.getMessage());
            response.setMessage(MessageConstant.GET_ALL_FAIL + MessageConstant.CATEGORY_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = ApiPath.CATEGORY_GET_UUID)
    public ResponseEntity<CategoryResponseDTO> getUUID(@RequestBody CategoryDTO request) {
        CategoryResponseDTO response = new CategoryResponseDTO();
        try {
            CategoryDTO data = service.findByUUid(request.getCategoryId());
            response.setData(data);
            response.setMessage("Success when get Product By Category ID");
            response.setMessage(MessageConstant.GET_BY_ID_OK + MessageConstant.CATEGORY_API);

            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception ex) {
            //log.error("Error when get Product By Category ID:", ex);
            log.error(MessageConstant.GET_BY_ID_FAIL + MessageConstant.CATEGORY_API, ex);
            //response.setMessage("Error when get Product By Category ID: " + ex.getMessage());
            response.setMessage(MessageConstant.GET_BY_ID_FAIL + MessageConstant.CATEGORY_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = ApiPath.CATEGORY_CREATE)
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryDTO request) {
        CategoryResponseDTO response = new CategoryResponseDTO();
        try {
            if (null == request) {
                response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean create = service.create(request);
            if (!create) {
                response.setMessage("Create Category fail!!!!");
                response.setMessage(MessageConstant.CREATE_CATEGORY_FAIL);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.setMessage("Success when create Category");
            response.setMessage(MessageConstant.CATEGORY_API + MessageConstant.CREATE_SUCCESS);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when create Category:", ex);
            log.error(MessageConstant.CATEGORY_API + MessageConstant.CREATE_FAILURE);
            response.setMessage("Error when create Category: " + ex.getMessage());
            response.setMessage(MessageConstant.GET_ALL_FAIL + MessageConstant.CATEGORY_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.CATEGORY_UPDATE)
    public ResponseEntity<CategoryResponseDTO> update(@RequestBody CategoryDTO request) {
        CategoryResponseDTO response = new CategoryResponseDTO();
        try {
            if (null == request) {
                response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean update = service.update(request);
            if (!update) {
                response.setMessage("Update Category fail!!!!");
                response.setMessage(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.CATEGORY_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.setMessage("Success when Update Category");
            response.setMessage(MessageConstant.SUCCESS_WHEN_UPDATE_CATEGORY + MessageConstant.CATEGORY_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            // log.error("Error when Update Category:", ex);
            log.error(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.CATEGORY_API, ex);
            //response.setMessage("Error when Update Category: " + ex.getMessage());
            response.setMessage(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.CATEGORY_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.CATEGORY_DELETE)
    public ResponseEntity<CategoryResponseDTO> delete(@RequestBody CategoryDTO request) {
        CategoryResponseDTO response = new CategoryResponseDTO();
        try {
            if (null == request) {
                response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getCategoryId())) {
                response.setMessage("Input Category Id");
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.CATEGORY_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.delete(request.getCategoryId());
            if (!delete) {
                //response.setMessage("Delete Category fail!!!!");
                response.setMessage(MessageConstant.CATEGORY_API + MessageConstant.DELETE_FAIL);

                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // response.setMessage("Success when delete Category");
            response.setMessage(MessageConstant.CATEGORY_API + MessageConstant.SUCCESS_WHEN_DELETE);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when delete Category:", ex);
            log.error(MessageConstant.CATEGORY_API + MessageConstant.ERROR_WHEN_DELETE);
            response.setMessage("Error when delete Category: " + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.CATEGORY_PERFORM_LOCK)
    public ResponseEntity<CategoryResponseDTO> performLock(@RequestBody CategoryDTO request) {
        CategoryResponseDTO response = new CategoryResponseDTO();
        try {
            if (null == request) {
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getCategoryId())) {
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.CATEGORY_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.performLock(request.getCategoryId());
            if (!delete) {
                response.setMessage(MessageConstant.CATEGORY_API + MessageConstant.UPDATE_STATUS_FAILURE);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.setMessage(MessageConstant.SUCCESS_PERFORM_LOCK_UNLOCK + MessageConstant.CATEGORY_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(MessageConstant.FAIL_PERFORM_LOCK_UNLOCK + MessageConstant.CATEGORY_API, ex);
            response.setMessage(MessageConstant.FAIL_PERFORM_LOCK_UNLOCK + MessageConstant.CATEGORY_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
