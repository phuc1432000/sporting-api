package com.sporting.api.controller;

import com.sporting.api.consts.ApiPath;
import com.sporting.api.consts.ErrorCode;
import com.sporting.api.consts.MessageConstant;
import com.sporting.api.dto.ProductDTO;
import com.sporting.api.response.ProductResponseDTO;
import com.sporting.api.service.ProductService;
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
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping(value = ApiPath.PRODUCT_GET_ALL)
    public ResponseEntity<ProductResponseDTO> getAll() {
        ProductResponseDTO response = new ProductResponseDTO();
        try {
            List<ProductDTO> list = service.findAll();
            response.setList(list);
            response.setMessage("Success when get all Product");
            response.setMessage(MessageConstant.GET_ALL_SUCCESS + MessageConstant.PRODUCT_API);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception ex) {
            //log.error("Error when get all Product:", ex);
            log.error(MessageConstant.GET_ALL_FAIL + MessageConstant.PRODUCT_API, ex);
            // response.setMessage("Error when get all Product: " + ex.getMessage());
            response.setMessage(MessageConstant.GET_ALL_FAIL + MessageConstant.PRODUCT_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = ApiPath.PRODUCT_GET_UUID)
    public ResponseEntity<ProductResponseDTO> getUUID(@RequestBody ProductDTO request) {
        ProductResponseDTO response = new ProductResponseDTO();
        try {
            ProductDTO data = service.findByUUid(request.getProductId());
            response.setData(data);
            //response.setMessage("Success when get Product By Product ID");
            response.setMessage(MessageConstant.GET_BY_ID_OK + MessageConstant.PRODUCT_API);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception ex) {
            //log.error("Error when get Product By Product ID:", ex);
            log.error(MessageConstant.GET_BY_ID_FAIL + MessageConstant.PRODUCT_API, ex);
            //response.setMessage("Error when get Product By Product ID: " + ex.getMessage());
            response.setMessage(MessageConstant.GET_BY_ID_FAIL + MessageConstant.PRODUCT_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = ApiPath.PRODUCT_CREATE)
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductDTO request) {
        ProductResponseDTO response = new ProductResponseDTO();
        try {
            if (null == request) {
                response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean create = service.create(request);
            if (!create) {
                //response.setMessage("Create Product fail!!!!");
                response.setMessage(MessageConstant.PRODUCT_API + MessageConstant.NON_EXISTED);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //response.setMessage("Success when create Product");
            response.setMessage(MessageConstant.PRODUCT_API + MessageConstant.CREATE_SUCCESS);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when create Product:", ex);
            response.setMessage(MessageConstant.GET_ALL_FAIL + MessageConstant.CREATE_FAILURE);
            //response.setMessage("Error when create Product: " + ex.getMessage());
            response.setMessage(MessageConstant.PRODUCT_API + MessageConstant.CREATE_FAILURE + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.PRODUCT_UPDATE)
    public ResponseEntity<ProductResponseDTO> update(@RequestBody ProductDTO request) {
        ProductResponseDTO response = new ProductResponseDTO();
        try {
            if (null == request) {
                response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean update = service.update(request);
            if (!update) {
                // response.setMessage("Update Product fail!!!!");
                response.setMessage(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.NON_EXISTED);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //response.setMessage("Success when Update Product");
            response.setMessage(MessageConstant.SUCCESS_WHEN_UPDATE + MessageConstant.PRODUCT_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when Update Product:", ex);
            response.setMessage(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.PRODUCT_API + ex);
            //response.setMessage("Error when Update Product: " + ex.getMessage());
            response.setMessage(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.PRODUCT_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.PRODUCT_DELETE)
    public ResponseEntity<ProductResponseDTO> delete(@RequestBody ProductDTO request) {
        ProductResponseDTO response = new ProductResponseDTO();
        try {
            if (null == request) {
                response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getProductId())) {
                //response.setMessage("Input Product Id");
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.PRODUCT_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.delete(request.getProductId());
            if (!delete) {
                //response.setMessage("Delete Product fail!!!!");
                response.setMessage(MessageConstant.PRODUCT_API + MessageConstant.DELETE_FAIL);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // response.setMessage("Success when delete Product");
            response.setMessage(MessageConstant.PRODUCT_API + MessageConstant.SUCCESS_WHEN_DELETE);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when delete Product:", ex);
            log.error(MessageConstant.PRODUCT_API + MessageConstant.ERROR_WHEN_DELETE, ex);
            //response.setMessage("Error when delete Product: " + ex.getMessage());
            response.setMessage(MessageConstant.PRODUCT_API + MessageConstant.ERROR_WHEN_DELETE + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.PRODUCT_PERFORM_LOCK)
    public ResponseEntity<ProductResponseDTO> performLock(@RequestBody ProductDTO request) {
        ProductResponseDTO response = new ProductResponseDTO();
        try {
            if (null == request) {
                //response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getProductId())) {
                // response.setMessage("Input Product Id");
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.PRODUCT_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.performLock(request.getProductId());
            if (!delete) {
                response.setMessage("Update Product Status fail!!!!");
                response.setMessage(MessageConstant.PRODUCT_API + MessageConstant.UPDATE_STATUS_FAILURE);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //response.setMessage("Success when perform lock/unlock Product");
            response.setMessage(MessageConstant.SUCCESS_PERFORM_LOCK_UNLOCK + MessageConstant.PRODUCT_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when perform lock/unlock Product:", ex);
            log.error(MessageConstant.FAIL_PERFORM_LOCK_UNLOCK + MessageConstant.PRODUCT_API, ex);
            //response.setMessage("Error when perform lock/unlock Product: " + ex.getMessage());
            response.setMessage(MessageConstant.FAIL_PERFORM_LOCK_UNLOCK + MessageConstant.PRODUCT_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
