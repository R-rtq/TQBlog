package com.rtq.controller;

import com.rtq.domain.ResponseResult;
import com.rtq.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author rtq
 * @Date 2022/12/30
 **/
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){

        return uploadService.upload(img);
    }
}
