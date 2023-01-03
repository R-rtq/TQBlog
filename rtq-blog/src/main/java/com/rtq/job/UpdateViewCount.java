package com.rtq.job;

import com.rtq.domain.entity.Article;
import com.rtq.service.ArticleService;
import com.rtq.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rtq
 * @Date 2023/1/3
 * redis定时器，定时将前端传入redis的数据保存到数据库
 **/
@Component
public class UpdateViewCount {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/59 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("Article:ViewCount");
        List<Article> articleList = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        //将浏览量存入sql数据库
        articleService.updateBatchById(articleList);
    }

}
