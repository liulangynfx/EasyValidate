package com.rainea.tool.easy.validate.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author liulang
 * @date 2019-12-28
 */
@Data
public class ComplexInfo {

    @NotBlank
    private String guid;

    @NotBlank
    private String emailStr;

    private Integer type;

    @Valid
    private SimpleInfo simpleInfo;

//    @NotEmpty
    private List<SimpleInfo> simpleInfoList;

//    @AssertTrue(message = "必须是男")
    private boolean sex;

    @AssertTrue(message = "guid 不能相等")
    public boolean notEqual() {
//        if (computer != null) {
//            return ! guid.equals(computer.getGuid());
//        }
        return false;
    }
}
