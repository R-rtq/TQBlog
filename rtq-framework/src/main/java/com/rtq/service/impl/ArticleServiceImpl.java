package com.rtq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rtq.constants.SystemConstants;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.Article;
import com.rtq.domain.entity.Category;
import com.rtq.domain.vo.ArticleDetailVo;
import com.rtq.domain.vo.ArticleListVo;
import com.rtq.domain.vo.HotArticleVo;
import com.rtq.domain.vo.PageVo;
import com.rtq.mapper.ArticleMapper;
import com.rtq.service.ArticleService;
import com.rtq.service.CategoryService;
import com.rtq.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author rtq
 * @Date 2022/12/22
 **/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page=new Page<>(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();


       /* //bean拷贝
       List<HotArticleVo> articleVos=new ArrayList<>();
        for (Article article : articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(article,vo);
            articleVos.add(vo);
        }*/
        List<HotArticleVo> vos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vos);
    }


        @Override
        public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
            //查询条件
            LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 如果 有categoryId 就要 查询时要和传入的相同
            lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
            // 状态是正式发布的
            lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
            // 对isTop进行降序
            lambdaQueryWrapper.orderByDesc(Article::getIsTop);

            //分页查询
            Page<Article> page = new Page<>(pageNum,pageSize);
            page(page,lambdaQueryWrapper);

            //查询categoryName
            List<Article> articles = page.getRecords();
            //通过categoryId去查询categoryName
            for (Article article : articles) {
                Category category = categoryService.getById(article.getCategoryId());
                article.setCategoryName(category.getName());
            }

            //封装查询结果
            List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);


            PageVo pageVo=new PageVo(articleListVos,page.getTotal());


            return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //转化成Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }

        //封装响应返回

        return ResponseResult.okResult(articleDetailVo);

    }
}
