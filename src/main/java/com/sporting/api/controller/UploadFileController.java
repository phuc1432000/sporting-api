package com.sporting.api.controller;

import com.sporting.api.consts.ApiPath;
import com.sporting.api.consts.ErrorCode;
import com.sporting.api.consts.MessageConstant;
import com.sporting.api.dto.ImageDTO;
import com.sporting.api.response.ImageResponseDTO;
import com.sporting.api.utils.BlobServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class UploadFileController {
    @Autowired
    BlobServiceUtils blobService;

    @PostMapping(value = ApiPath.UPLOAD_FILE)
    public ResponseEntity<ImageResponseDTO> uploadFile(@RequestPart MultipartFile file) {
        ImageResponseDTO response = new ImageResponseDTO();
        try {
            if (null == file) {
                response.setMessage("Input body");
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //upload file
            String fileName = blobService.uploadFile(file.getInputStream(), file.getOriginalFilename());
            String getUrl = blobService.getUrl(fileName);
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setFileUploadUrl(getUrl);
            response.setData(imageDTO);
            response.setMessage("Success when upload file");
            response.setErrorCode(ErrorCode.SUCCESS);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error when upload  file:", ex);
            response.setMessage("Error when upload file: " + ex.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
