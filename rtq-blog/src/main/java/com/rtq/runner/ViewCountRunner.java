package com.rtq.runner;


import com.rtq.domain.entity.Article;
import com.rtq.mapper.ArticleMapper;
import com.rtq.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rtq
 * @Date 2023/1/3
 * 初始化数据库中的数据到redis中，防止第一次运行redis中没有缓存，以及防止redis意外关闭没有数据
 **/
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();//
                }));
        //存储到redis中
        redisCache.setCacheMap("Article:ViewCount",viewCountMap);
    }
}
