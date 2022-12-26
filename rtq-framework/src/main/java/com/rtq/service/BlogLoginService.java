package com.rtq.service;

import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.User;

/**
 * @author rtq
 * @Date 2022/12/26
 **/
public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
