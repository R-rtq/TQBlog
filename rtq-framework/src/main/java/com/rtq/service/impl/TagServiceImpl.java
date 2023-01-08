package com.rtq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.dto.TagListDto;
import com.rtq.domain.entity.LoginUser;
import com.rtq.domain.entity.Tag;
import com.rtq.domain.vo.PageVo;
import com.rtq.domain.vo.TagVo;
import com.rtq.enums.AppHttpCodeEnum;
import com.rtq.exception.SystemException;
import com.rtq.mapper.TagMapper;
import com.rtq.service.TagService;

import com.rtq.utils.BeanCopyUtils;
import com.rtq.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-01-04 19:45:25
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());


        Page<Tag> page=new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //封装数据返回
//        List<Tag> records = page.getRecords();
//        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(records, TagVo.class);
        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        if (!StringUtils.hasText(tagListDto.getRemark())&&!StringUtils.hasText(tagListDto.getName())){
            throw new SystemException(AppHttpCodeEnum.TAG_NOT_NULL);
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Tag tag = new Tag();
        tag.setCreateBy(loginUser.getUser().getId());
        tag.setUpdateBy(loginUser.getUser().getUpdateBy());
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        save(tag);
        return ResponseResult.okResult() ;
    }

    @Override
    public ResponseResult delTag(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}
