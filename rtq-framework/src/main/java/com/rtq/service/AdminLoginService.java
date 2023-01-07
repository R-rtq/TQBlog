package com.rtq.service;

import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.User;

/**
 * @author rtq
 * @Date 2023/1/4
 **/
public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
