package com.rainea.tool.easy.validate.request.model.user;

import lombok.Data;

import java.util.List;

/**
 * 登录用户
 */
@Data
public class LoginUser {

    /**
     * 用户guid
     */
    private String guid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 账户状态
     */
    private Boolean accountStatus;

    /**
     * 角色集合
     */
    private List<Integer> userRoles;

    /**
     * 城市guid
     */
    private String cityGuid;

    /**
     * 职位
     */
    private Integer userOffice;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 用户设备id
     */
    private String clientId;
}
