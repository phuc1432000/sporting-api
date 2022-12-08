package com.sporting.api.controller;

import com.sporting.api.consts.ApiPath;
import com.sporting.api.consts.ErrorCode;
import com.sporting.api.consts.MessageConstant;
import com.sporting.api.dto.OrderDetailDTO;
import com.sporting.api.response.OrderDetailResponseDTO;
import com.sporting.api.service.OrderDetailService;
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
public class OrderController {
    @Autowired
    OrderDetailService service;

    @GetMapping(value = ApiPath.ORDER_DETAIL_GET_ALL)
    public ResponseEntity<OrderDetailResponseDTO> getAll() {
        OrderDetailResponseDTO response = new OrderDetailResponseDTO();
        try {
            List<OrderDetailDTO> list = service.findAll();
            response.setList(list);
            //response.setMessage("Success when get all" + MessageConstant.ORDER_API);
            response.setMessage(MessageConstant.GET_ALL_SUCCESS + MessageConstant.ORDER_API);
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (Exception ex) {
            //log.error("Error when get all:" + MessageConstant.ORDER_API, ex);
            log.error(MessageConstant.GET_ALL_FAIL + MessageConstant.ORDER_API, ex);
            //response.setMessage("Error when get all" + MessageConstant.ORDER_API + ex.getMessage());
            response.setMessage(MessageConstant.GET_ALL_FAIL + MessageConstant.ORDER_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = ApiPath.ORDER_DETAIL_GET_UUID)
    public ResponseEntity<OrderDetailResponseDTO> getUuid(@RequestBody OrderDetailDTO request) {
        OrderDetailResponseDTO response = new OrderDetailResponseDTO();
        try {
            OrderDetailDTO data = service.findByUUid(request.getOrderId());
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

    @PostMapping(value = ApiPath.ORDER_DETAIL_CREATE)
    public ResponseEntity<OrderDetailResponseDTO> create(@RequestBody OrderDetailDTO request) {
        OrderDetailResponseDTO response = new OrderDetailResponseDTO();
        try {
            if (null == request) {
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean create = service.create(request);
            if (!create) {
                // response.setMessage("Create Product fail!!!!");
                response.setMessage(MessageConstant.CREATE_FAILURE + MessageConstant.NON_EXISTED);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //response.setMessage("Success when create Product");
            response.setMessage(MessageConstant.CREATE_SUCCESS + MessageConstant.ORDER_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when create" + MessageConstant.CART_API + ":", ex);
            log.error(MessageConstant.CREATE_FAILURE + MessageConstant.ORDER_API + ":", ex);
            //response.setMessage("Error when create Product: " + ex.getMessage());
            response.setMessage(MessageConstant.ORDER_API + MessageConstant.CREATE_FAILURE + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.ORDER_DETAIL_UPDATE)
    public ResponseEntity<OrderDetailResponseDTO> update(@RequestBody OrderDetailDTO request) {
        OrderDetailResponseDTO response = new OrderDetailResponseDTO();
        try {
            if (null == request) {
                response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean update = service.update(request);
            if (!update) {
                //response.setMessage("Update Product fail!!!!");
                response.setMessage(MessageConstant.ORDER_API + MessageConstant.NON_EXISTED);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // response.setMessage("Success when Update Product");
            response.setMessage(MessageConstant.SUCCESS_WHEN_UPDATE + MessageConstant.ORDER_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when Update Product:", ex);
            log.error(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.ORDER_API, ex);
            //response.setMessage("Error when Update Product: " + ex.getMessage());
            response.setMessage(MessageConstant.FAIL_WHEN_UPDATE + MessageConstant.ORDER_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.ORDER_DETAIL_DELETE)
    public ResponseEntity<OrderDetailResponseDTO> delete(@RequestBody OrderDetailDTO request) {
        OrderDetailResponseDTO response = new OrderDetailResponseDTO();
        try {
            if (null == request) {
                //response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getOrderId())) {
                //response.setMessage("Input Product Id");
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.ORDER_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.delete(request.getOrderId());
            if (!delete) {
                //response.setMessage("Delete Product fail!!!!");
                response.setMessage(MessageConstant.ORDER_API + MessageConstant.DELETE_FAIL);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //response.setMessage("Success when delete Product");
            response.setMessage(MessageConstant.ORDER_API + MessageConstant.SUCCESS_WHEN_UPDATE);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when delete Product:", ex);
            log.error(MessageConstant.ORDER_API + MessageConstant.ERROR_WHEN_DELETE, ex);
            //response.setMessage("Error when delete Product: " + ex.getMessage());
            response.setMessage(MessageConstant.ORDER_API + MessageConstant.ERROR_WHEN_DELETE + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.ORDER_DETAIL_PERFORM_LOCK)
    public ResponseEntity<OrderDetailResponseDTO> performLock(@RequestBody OrderDetailDTO request) {
        OrderDetailResponseDTO response = new OrderDetailResponseDTO();
        try {
            if (null == request) {
                response.setMessage("Input body");
                response.setMessage(MessageConstant.INPUT_BODY);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!StringUtils.isNotBlank(request.getOrderId())) {
                response.setMessage("Input Product Id");
                response.setMessage(MessageConstant.INPUT_ID + MessageConstant.ORDER_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean delete = service.performLock(request.getOrderId());
            if (!delete) {
                //response.setMessage("Update Product Status fail!!!!");
                response.setMessage(MessageConstant.UPDATE_STATUS_FAILURE + MessageConstant.ORDER_API);
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.setMessage("Success when perform lock/unlock Product");
            response.setMessage(MessageConstant.SUCCESS_PERFORM_LOCK_UNLOCK + MessageConstant.ORDER_API);
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            //log.error("Error when perform lock/unlock Product:", ex);
            log.error(MessageConstant.FAIL_PERFORM_LOCK_UNLOCK + MessageConstant.ORDER_API, ex);
            // response.setMessage("Error when perform lock/unlock Product: " + ex.getMessage());
            response.setMessage(MessageConstant.FAIL_PERFORM_LOCK_UNLOCK + MessageConstant.ORDER_API + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
