package com.rtq.controller;

import com.rtq.domain.ResponseResult;
import com.rtq.domain.entity.AdminUserInfoVo;
import com.rtq.domain.entity.LoginUser;
import com.rtq.domain.entity.User;
import com.rtq.domain.vo.UserInfoVo;
import com.rtq.enums.AppHttpCodeEnum;
import com.rtq.exception.SystemException;
import com.rtq.service.AdminLoginService;
import com.rtq.service.BlogLoginService;
import com.rtq.service.MenuService;
import com.rtq.service.RoleService;
import com.rtq.utils.BeanCopyUtils;
import com.rtq.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rtq
 * @Date 2023/1/4
 **/
@RestController
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        if (!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);

    }
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据id查询权限信息
        List<String> perms=menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息roleKey
        List<String> roleKeyList=roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo=new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);

    }



}
