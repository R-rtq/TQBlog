package com.rtq.controller;

import com.rtq.annotation.SystemLog;
import com.rtq.domain.ResponseResult;
import com.rtq.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @SystemLog(businessName = "写文章上传缩略图")
    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile img) {
        try {
            return uploadService.uploadImg(img);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传上传失败");
        }
    }
}