package com.rtq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rtq.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author rtq
 * @Date 2022/12/30
 **/
public interface UploadService  {
    ResponseResult upload(MultipartFile img);

    ResponseResult uploadImg(MultipartFile img) throws IOException;
}
