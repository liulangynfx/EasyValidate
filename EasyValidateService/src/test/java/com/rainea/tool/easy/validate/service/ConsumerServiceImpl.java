package com.rainea.tool.easy.validate.service;

import com.rainea.tool.easy.validate.model.ComplexInfo;
import com.rainea.tool.easy.validate.model.SimpleInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author liulang
 * @date 2019-12-28
 */
public class ConsumerServiceImpl {

    public void buySomething(@NotBlank(message = "订单号不为空") String serialNo, Double payPrice, @Valid SimpleInfo simpleInfo) {

    }

    public void buyComplexInfo(@Valid ComplexInfo complexInfo) {

    }
}
