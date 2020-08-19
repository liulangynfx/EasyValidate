package com.rainea.tool.easy.validate.utils;

import com.rainea.tool.easy.validate.exception.ParamInvalidException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 返回值委托类
 *
 * @author liulang
 * @date 2019-12-30
 */
public class ResultDelegation {

    private static final Map<Class, Function<String, Object>> RESULT_MAP = new HashMap<>();

    public <T> Object getResult(Class<T> returnType, String invalidProperty) {
        Function<String, Object> function = RESULT_MAP.get(returnType);
        if (function == null) {
            throw new ParamInvalidException("invalid param: " + invalidProperty);
        }
        return function.apply(invalidProperty);
    }

    /**
     * 注册结果处理类
     *
     * @param clazz 返回值类型
     * @param function 非法字段的返回值处理
     */
    @SuppressWarnings("unused")
    public void register(Class clazz, Function<String, Object> function) {
        if (clazz == null || function == null) {
            throw new NullPointerException();
        }
        RESULT_MAP.put(clazz, function);
    }

}
