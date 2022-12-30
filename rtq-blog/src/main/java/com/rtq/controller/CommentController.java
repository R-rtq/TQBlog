package com.rtq.controller;

import com.rtq.constants.SystemConstants;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.Comment;
import com.rtq.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author rtq
 * @Date 2022/12/26
 **/
@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    //文章评论列表
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){

        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }
    //友链评论列表
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
