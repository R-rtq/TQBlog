package com.rtq.service.impl;

import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.LoginUser;
import com.rtq.domain.entity.User;
import com.rtq.domain.vo.BlogUserLoginVo;
import com.rtq.domain.vo.UserInfoVo;
import com.rtq.service.AdminLoginService;
import com.rtq.utils.BeanCopyUtils;
import com.rtq.utils.JwtUtil;
import com.rtq.utils.RedisCache;
import com.rtq.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author rtq
 * @Date 2023/1/4
 **/
@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名密码错误");

        }
        //获取userid生成token
        LoginUser loginUser=(LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入Redis
        redisCache.setCacheObject("adminlogin:"+userId,loginUser);
        //把token封装 返回
        Map<String,String> map=new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取当前登录的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中对应的值
        redisCache.deleteObject("adminlogin:"+userId);
        return ResponseResult.okResult();

    }
}
