package com.rainea.tool.easy.validate.exception;

/**
 * 参数校验异常
 *
 * @author liulang
 * @date 2019-12-26
 */
public class ParamInvalidException extends RuntimeException {

    private static final long serialVersionUID = 2371769605212052726L;

    public ParamInvalidException(String msg) {
        super(msg);
    }
}
