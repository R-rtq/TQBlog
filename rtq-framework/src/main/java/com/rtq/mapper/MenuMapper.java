package com.rtq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rtq.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-05 13:56:11
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);
}