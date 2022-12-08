package com.sporting.api.controller;

import com.sporting.api.consts.ApiPath;
import com.sporting.api.consts.ErrorCode;
import com.sporting.api.consts.MessageConstant;
import com.sporting.api.dto.CartDetailDTO;
import com.sporting.api.response.CartDetailResponseDTO;
import com.sporting.api.service.CartDetailService;
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
public class CartController {
    @Autowired
    CartDetailService service;

    @GetMapping(value = ApiPath.CART_DETAIL_GET_ALL)
    public ResponseEntity<CartDetailResponseDTO> getAll() {
        CartDetailResponseDTO response = new CartDetailResponseDTO();
        try {
            List<CartDetailDTO> list = service.findAll();
            response.setList(list);
           // response.setMessage("Success when get all" + MessageConstant.CART_API);
            response.setMessage(MessageConstant.GET_ALL_SUCCESS + MessageConstant.CART_API);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception ex) {
            //log.error("Error when get all:" + MessageConstant.CART_API, ex);
            log.error(MessageConstant.GET_ALL_FAIL + MessageConstant.CART_API, ex);
            //response.setMessage("Error when get all" + MessageConstant.CART_API + ex.getMessage());
            response.setMessage(MessageConstant.GET_ALL_FAIL + MessageConstant.CART_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = ApiPath.CART_DETAIL_GET_UUID)
    public ResponseEntity<CartDetailResponseDTO> getUuid(@RequestBody CartDetailDTO request) {
        CartDetailResponseDTO response = new CartDetailResponseDTO();
        try {
            CartDetailDTO data = service.findByUUid(request.getCartId());
            response.setData(data);
            response.setMessage(MessageConstant.GET_BY_ID_OK + MessageConstant.CART_API);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception ex) {
            log.error(MessageConstant.GET_BY_ID_FAIL + MessageConstant.CART_API, ex);
            response.setMessage(MessageConstant.GET_BY_ID_FAIL + MessageConstant.CART_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = ApiPath.CART_DETAIL_CREATE)
    public ResponseEntity<CartDetailResponseDTO> create(@RequestBody CartDetailDTO request) {
        CartDetailResponseDTO response = new CartDetailResponseDTO();
        try {
            if (null == request) {
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean create = service.create(request);
            if (!create) {
                //response.setMessage("Create Product fail!!!!");
                response.setMessage(MessageConstant.CART_API + MessageConstant.CREATE_FAILURE );
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
           // response.setMessage("Success when create Product");
            response.setMessage(MessageConstant.CART_API + MessageConstant.CART_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when create" + MessageConstant.CART_API + ":", ex);
            log.error(MessageConstant.CART_API + MessageConstant.CREATE_FAILURE, ex);
            //response.setMessage("Error when create Product: " + ex.getMessage());
            response.setMessage(MessageConstant.CART_API + MessageConstant.CREATE_FAILURE + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.CART_DETAIL_UPDATE)
    public ResponseEntity<CartDetailResponseDTO> update(@RequestBody CartDetailDTO request) {
        CartDetailResponseDTO response = new CartDetailResponseDTO();
        try {
            if (null == request) {
                //response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean update = service.update(request);
            if (!update) {
                //response.setMessage("Update Product fail!!!!");
                response.setMessage(MessageConstant.CART_API + MessageConstant.UPDATE_STATUS_FAILURE);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //response.setMessage("Success when Update Product");
            response.setMessage(MessageConstant.CART_API + MessageConstant.SUCCESS_WHEN_UPDATE);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when Update Product:", ex);
            log.error(MessageConstant.CART_API + MessageConstant.FAIL_WHEN_UPDATE, ex);
            //response.setMessage("Error when Update Product: " + ex.getMessage());
            response.setMessage(MessageConstant.UPDATE_STATUS_FAILURE + MessageConstant.CART_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.CART_DETAIL_DELETE)
    public ResponseEntity<CartDetailResponseDTO> delete(@RequestBody CartDetailDTO request) {
        CartDetailResponseDTO response = new CartDetailResponseDTO();
        try {
            if (null == request) {
                //response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getCartId())) {
                //response.setMessage("Input Product Id");
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.CART_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.delete(request.getCartId());
            if (!delete) {
                //response.setMessage("Delete Product fail!!!!");
                response.setMessage(MessageConstant.DELETE_FAIL + MessageConstant.CART_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
           // response.setMessage("Success when delete Product");
            response.setMessage(MessageConstant.SUCCESS_WHEN_DELETE + MessageConstant.CART_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
           // log.error("Error when delete Product:", ex);
            log.error(MessageConstant.CART_API + MessageConstant.ERROR_WHEN_DELETE, ex);
            //response.setMessage("Error when delete Product: " + ex.getMessage());
            response.setMessage(MessageConstant.CART_API + MessageConstant.ERROR_WHEN_DELETE + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.CART_DETAIL_PERFORM_LOCK)
    public ResponseEntity<CartDetailResponseDTO> performLock(@RequestBody CartDetailDTO request) {
        CartDetailResponseDTO response = new CartDetailResponseDTO();
        try {
            if (null == request) {
                //response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getCartId())) {
                //response.setMessage("Input Product Id");
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.CART_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.performLock(request.getCartId());
            if (!delete) {
                //response.setMessage("Update Product Status fail!!!!");
                response.setMessage(MessageConstant.CART_API + MessageConstant.UPDATE_STATUS_FAILURE);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //response.setMessage("Success when perform lock/unlock Product");
            response.setMessage(MessageConstant.CART_API + MessageConstant.SUCCESS_PERFORM_LOCK_UNLOCK);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when perform lock/unlock Product:", ex);
            log.error(MessageConstant.CART_API + MessageConstant.FAIL_PERFORM_LOCK_UNLOCK, ex);
            //response.setMessage("Error when perform lock/unlock Product: " + ex.getMessage());
            response.setMessage(MessageConstant.CART_API + MessageConstant.FAIL_PERFORM_LOCK_UNLOCK + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
