package com.rtq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-12-26 21:52:26
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

}
