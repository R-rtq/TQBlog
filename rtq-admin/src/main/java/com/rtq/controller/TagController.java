package com.rtq.controller;

import com.rtq.domain.ResponseResult;
import com.rtq.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rtq
 * @Date 2023/1/4
 **/
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;
    @GetMapping("/list")
    public ResponseResult list(){
        return ResponseResult.okResult(tagService.list());
    }
}
