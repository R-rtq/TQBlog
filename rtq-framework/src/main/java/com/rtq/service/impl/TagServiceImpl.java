package com.rtq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rtq.domain.entity.Tag;
import com.rtq.mapper.TagMapper;
import com.rtq.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-01-04 19:45:25
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
