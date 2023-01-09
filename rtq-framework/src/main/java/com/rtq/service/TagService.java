package com.rtq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.dto.TagListDto;
import com.rtq.domain.entity.Tag;
import com.rtq.domain.vo.PageVo;
import com.rtq.domain.vo.TagVo;
import com.rtq.domain.vo.TagVo2;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-01-04 19:45:22
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult delTag(Long id);

    List<TagVo2> listAllTag();
}
