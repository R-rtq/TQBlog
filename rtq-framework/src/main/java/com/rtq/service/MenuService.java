package com.rtq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rtq.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-01-05 13:56:32
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);
}
