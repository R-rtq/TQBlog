package com.rtq.controller;

import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.Article;
import com.rtq.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rtq
 * @Date 2022/12/22
 **/
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    //@GetMapping("/")
//    @RequestMapping(value = "/list",method = RequestMethod.GET)
//    public List<Article> test(){
//        return articleService.list();
//    }
//
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章，封装成responseResult返回
        ResponseResult result=articleService.hotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

}
