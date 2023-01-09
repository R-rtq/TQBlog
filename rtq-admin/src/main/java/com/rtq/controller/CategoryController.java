package com.rtq.controller;

import com.rtq.annotation.SystemLog;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.vo.CategoryVo;
import com.rtq.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rtq
 * @Date 2023/1/8
 **/
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @SystemLog(businessName = "写文章时获取所选分类")
    @GetMapping("/listAllCategory")
    public ResponseResult getCategory(){
       List<CategoryVo> list= categoryService.listAllCategory();
       return ResponseResult.okResult(list);

    }
}
