package com.rtq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rtq.constants.SystemConstants;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.Comment;
import com.rtq.domain.vo.CommentVo;
import com.rtq.domain.vo.PageVo;
import com.rtq.enums.AppHttpCodeEnum;
import com.rtq.exception.SystemException;
import com.rtq.mapper.CommentMapper;
import com.rtq.service.CommentService;
import com.rtq.service.UserService;
import com.rtq.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-12-26 21:52:29
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        //
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //判断是否为根评论rootId为-1

        queryWrapper.eq(Comment::getRootId,-1);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);

        //分页查询
        Page<Comment> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children=getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children
             );
        }

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    //文章添加评论
    @Override
    public ResponseResult addComment(Comment comment) {
        //获取createBy
//        Long userId = SecurityUtils.getUserId();
//        comment.setCreateBy(userId);
        //评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }



    //子评论
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment> commentList = list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(commentList);
        return commentVos;

    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过creatyBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}

