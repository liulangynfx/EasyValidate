package com.rainea.tool.easy.validate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字段结果
 *
 * @author liulang
 * @date 2019-12-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldResult {

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段校验结果
     */
    private String errorMsg;
}
