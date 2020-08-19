package com.rainea.tool.easy.validate.aop;

import com.rainea.tool.easy.validate.exception.ParamInvalidException;
import com.rainea.tool.easy.validate.model.FieldResult;
import com.rainea.tool.easy.validate.utils.ParamValidateUtil;
import com.rainea.tool.easy.validate.utils.ResultDelegation;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 参数校验切面类，使用时将此类注入spring容器中即可，默认校验不通过抛出{@link ParamInvalidException}异常，可以通过此类提供的setter
 * 方法重新设置 resultDelegation，或者修改此类默认提供的 resultDelegation
 *
 * @author liulang
 * @date 2019-12-26
 */
@Component
@Aspect
@Getter
@Setter
public class ParamValidateAspect {

    private ResultDelegation resultDelegation;

    public ParamValidateAspect() {
        resultDelegation = new ResultDelegation();
    }

    /**
     * 配置切点
     */
    @Pointcut("@annotation(com.rainea.tool.easy.validate.annotation.Validate)")
    public void run() {
        //for aop
    }

    /**
     * 配置通知
     *
     * @param joinPoint 切点
     * @return 返回对象
     * @throws Throwable 执行异常
     */
    @Around("run()")
    public Object aroundRun(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        //判断返回值
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Class returnType = methodSignature.getReturnType();
        Method method = methodSignature.getMethod();
        Object target = joinPoint.getTarget();

        //校验方法
        FieldResult fieldResult = ParamValidateUtil.validateMethod(target, method, args);

        //校验通过
        if (fieldResult == null) {
            return joinPoint.proceed(args);
        }

        //校验失败，判断返回值类型
        return resultDelegation.getResult(returnType, fieldResult.getFieldName());
    }

}
