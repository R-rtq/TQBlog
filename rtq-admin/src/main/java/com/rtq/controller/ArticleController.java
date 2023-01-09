package com.rtq.controller;

import com.rtq.annotation.SystemLog;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.dto.AddArticleDto;
import com.rtq.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rtq
 * @Date 2023/1/9
 **/
@RestController
@RequestMapping("/content/article")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @SystemLog(businessName = "添加文章内容")
    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

}
