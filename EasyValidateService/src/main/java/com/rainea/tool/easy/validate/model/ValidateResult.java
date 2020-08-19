package com.rainea.tool.easy.validate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liulang
 * @date 2019-12-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class ValidateResult {

    /**
     * 原始入参
     */
    protected Object[] arguments;

    /**
     * 校验信息
     */
    private transient Set<ConstraintViolation<Object>> constraintViolations;

    /**
     * 方法
     */
    protected Method method;

    /**
     * 目标实例
     */
    protected Object target;

    /**
     * 打印信息
     */
    public void printErrorMsg() {
        ValidateResultPrinter printer = build2Printer();
        if (hasErrors()) {
            log.error("Param validate info: {}", printer.toString());
        } else {
            log.debug("Param validate info: {}", printer.toString());
        }
    }

    /**
     * 获取第一个非法属性
     *
     * @return FieldResult类型
     */
    public FieldResult getInvalidProperty() {
        if (hasErrors()) {
            ConstraintViolation<Object> firstField = constraintViolations.iterator().next();
            String name = getPropertyName(firstField);
            return new FieldResult(name, firstField.getMessage());
        }
        return null;
    }

    /**
     * 是否有校验错误
     *
     * @return true - 有错误，否则无错误
     */
    private boolean hasErrors() {
        return ! CollectionUtils.isEmpty(constraintViolations);
    }

    /**
     * 获取简单的属性名
     *
     * @param constraintViolation 约束校验
     * @return 简单的属性名
     */
    private String getPropertyName(ConstraintViolation constraintViolation) {
        return String.valueOf(((PathImpl)constraintViolation.getPropertyPath()).getLeafNode());
    }

    /**
     * 创建输出对象
     *
     * @return 输出对象
     */
    private ValidateResultPrinter build2Printer() {
        List<FieldResult> fieldResults = constraintViolations.stream()
                .map(x -> new FieldResult(getPropertyName(x), x.getMessage()))
                .collect(Collectors.toList());
        ValidateResultPrinter.ValidateResultPrinterBuilder builder = ValidateResultPrinter.builder()
                .arguments(this.getArguments())
                .errorField(fieldResults);

        if (this.getMethod() != null) {
            builder.method(this.getMethod().getName());
        }
        if (this.getTarget() != null) {
            builder.clazz(this.getTarget().getClass().getName());
        }
        return builder.build();
    }

    /**
     * 输出对象
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    private static class ValidateResultPrinter {

        /**
         * 类名
         */
        private String clazz;

        /**
         * 方法名
         */
        private String method;

        /**
         * 原始参数
         */
        protected Object[] arguments;

        /**
         * 非法属性
         */
        private List<FieldResult> errorField;
    }

}
