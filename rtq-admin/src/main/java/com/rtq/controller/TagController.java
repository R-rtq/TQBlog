package com.rtq.controller;

import com.rtq.domain.ResponseResult;
import com.rtq.domain.dto.EditTagDto;
import com.rtq.domain.dto.TagListDto;
import com.rtq.domain.entity.Tag;
import com.rtq.domain.vo.PageVo;
import com.rtq.service.TagService;
import com.rtq.utils.BeanCopyUtils;
import com.rtq.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author rtq
 * @Date 2023/1/4
 **/
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){

        return tagService.addTag(tagListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delTag(@PathVariable Long id){
        return tagService.delTag(id);

    }

    @PutMapping
    public ResponseResult edit(@RequestBody EditTagDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto,Tag.class);
        tag.setUpdateBy(SecurityUtils.getLoginUser().getUser().getId());
        tag.setUpdateTime(new Date());
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }


    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }


}
