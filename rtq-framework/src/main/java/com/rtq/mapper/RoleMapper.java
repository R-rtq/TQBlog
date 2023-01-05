package com.rtq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rtq.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-05 14:21:37
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}
