package com.rtq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.User;
import com.rtq.domain.vo.UserInfoVo;
import com.rtq.mapper.UserMapper;
import com.rtq.service.UserService;
import com.rtq.utils.BeanCopyUtils;
import com.rtq.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-12-26 23:08:54
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成vo返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }
}
