package com.rainea.tool.easy.validate.util;

import com.alibaba.fastjson.JSON;
import com.rainea.tool.easy.validate.model.ComplexInfo;
import com.rainea.tool.easy.validate.model.FieldResult;
import com.rainea.tool.easy.validate.model.SimpleInfo;
import com.rainea.tool.easy.validate.service.ConsumerServiceImpl;
import com.rainea.tool.easy.validate.utils.ParamValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author liulang
 * @date 2019-12-28
 */
@Slf4j
public class ParamValidateTest {

    @Test
    public void testModel() {
        log.info("testModel testModel");
        ComplexInfo info = new ComplexInfo();
        info.setEmailStr("555");
        info.setGuid("234324");

        SimpleInfo simpleInfo = new SimpleInfo();
        simpleInfo.setGuid("sdfdsf");
        simpleInfo.setName("电脑");

        info.setSimpleInfo(simpleInfo);
//        info.setComputerList(Arrays.asList(computer));

        FieldResult fieldResult = ParamValidateUtil.validateObject(info);
        System.out.println("result： " + JSON.toJSONString(fieldResult));
    }

    @Test
    public void testMethod() throws NoSuchMethodException {
        ConsumerServiceImpl service = new ConsumerServiceImpl();

        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        ExecutableValidator executableValidator = validator.forExecutables();

        Method method = ConsumerServiceImpl.class.getMethod("buySomething", String.class, Double.class, SimpleInfo.class);
        SimpleInfo simpleInfo = new SimpleInfo();
        simpleInfo.setGuid("computer1");
        Object[] params = {"234", 12.3, simpleInfo};

        Set<ConstraintViolation<ConsumerServiceImpl>> constraintViolations =
                executableValidator.validateParameters(service, method, params);
    }

    @Test
    public void testComplexInfo() throws NoSuchMethodException {
        ConsumerServiceImpl service = new ConsumerServiceImpl();

        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        ExecutableValidator executableValidator = validator.forExecutables();

        Method method = ConsumerServiceImpl.class.getMethod("buyComplexInfo", ComplexInfo.class);
        ComplexInfo complexInfo = new ComplexInfo();
        complexInfo.setGuid("123");
        complexInfo.setEmailStr("324@wer");
        SimpleInfo simpleInfo = new SimpleInfo();
        simpleInfo.setGuid("123");
        complexInfo.setSimpleInfo(simpleInfo);
        Object[] params = {complexInfo};

        Set<ConstraintViolation<ConsumerServiceImpl>> constraintViolations =
                executableValidator.validateParameters(service, method, params);
    }
}
