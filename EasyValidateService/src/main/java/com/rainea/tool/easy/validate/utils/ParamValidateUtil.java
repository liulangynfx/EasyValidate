package com.rainea.tool.easy.validate.utils;

import com.rainea.tool.easy.validate.model.FieldResult;
import com.rainea.tool.easy.validate.model.ValidateResult;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author liulang
 * @date 2019-12-26
 */
public class ParamValidateUtil {

    private ParamValidateUtil() {}

    private static Validator validator = Validation.byProvider(HibernateValidator.class)
            .configure().failFast(true).buildValidatorFactory().getValidator();

    private static ExecutableValidator executableValidator = validator.forExecutables();

    /**
     * 校验方法
     * @param object 目标实例
     * @param method 方法
     * @param parameterValues 参数
     * @param groups 校验组
     * @return 错误属性名
     */
    public static FieldResult validateMethod(Object object,
                                             Method method,
                                             Object[] parameterValues,
                                             Class<?>... groups) {

        Set<ConstraintViolation<Object>> constraintViolations =
                executableValidator.validateParameters(object, method, parameterValues, groups);
        ValidateResult validateResult = ValidateResult.builder().arguments(parameterValues)
                .constraintViolations(constraintViolations)
                .target(object)
                .method(method)
                .build();
        return analysisConstraintViolation(validateResult);
    }

    /**
     * 检验对象
     *
     * @param object 需要检验的对象
     * @return 错误属性
     */
    public static FieldResult validateObject(Object object) {
        if (object == null) {
            return null;
        }
        Set<ConstraintViolation<Object>> validateRes = validator.validate(object);
        Object[] args = {object};

        ValidateResult validateResult = ValidateResult.builder().arguments(args)
                .constraintViolations(validateRes)
                .target(null)
                .method(null)
                .build();
        return analysisConstraintViolation(validateResult);
    }

    /**
     * 解析判断结果
     *
     * @param validateResult 校验结果
     * @return 解析结果
     */
    private static FieldResult analysisConstraintViolation(ValidateResult validateResult) {
        validateResult.printErrorMsg();
        return validateResult.getInvalidProperty();
    }
}
