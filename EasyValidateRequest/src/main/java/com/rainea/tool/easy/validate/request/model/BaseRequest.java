package com.rainea.tool.easy.validate.request.model;


import com.rainea.tool.easy.validate.request.model.user.LoginUser;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author liulang
 * @date 2019-12-27
 */
@Data
public class BaseRequest {

    /**
     * token
     */
    @NotBlank(message = "token为空")
    protected String token;

    /**
     * 版本号
     */
    @NotBlank(message = "版本信息为空")
    protected String version;

    /**
     * 当前登录人
     */
    @NotNull(message = "用户未登录")
    protected LoginUser loginUser;
}
