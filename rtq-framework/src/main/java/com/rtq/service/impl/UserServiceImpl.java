package com.rtq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rtq.domain.entity.User;
import com.rtq.mapper.UserMapper;
import com.rtq.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-12-26 23:08:54
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
