package com.rtq.domain.entity;

import com.rtq.domain.vo.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author rtq
 * @Date 2023/1/5
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {
    private List<String> permissions;

    private List<String> roles;

    private UserInfoVo userInfoVo;
}
