package com.rainea.tool.easy.validate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liulang
 * @date 2019-12-28
 */
@Data
public class SimpleInfo {

    @NotBlank(message = "computer guid 不为空")
    private String guid;

    private String name;

//    @NotBlank
//    @Min(1)
    private Double price;


}
