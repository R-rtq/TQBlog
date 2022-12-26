package com.rtq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.Category;




/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-12-24 18:30:49
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

}
