package com.rtq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rtq.constants.SystemConstants;
import com.rtq.domain.entity.LoginUser;
import com.rtq.domain.entity.User;
import com.rtq.mapper.MenuMapper;
import com.rtq.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author rtq
 * @Date 2022/12/26
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(lambdaQueryWrapper);

        //判断是否查到用户，没有查到抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }

        //返回用户信息
        //TODO 查询权限封装信息
        //todo 如果是后台用户才需要查询权限封装
        //管理员
        if (user.getType().equals(SystemConstants.ADMAIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        return new LoginUser(user,null);
    }
}
