package com.rtq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rtq.domain.entity.ArticleTag;
import com.rtq.mapper.ArticleTagMapper;
import com.rtq.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-01-09 17:30:09
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
