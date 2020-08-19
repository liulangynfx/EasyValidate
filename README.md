基于spring的参数校验工具
========

功能介绍
-----
这是一个参数校验的框架或者插件，基于hibernate validator和spring AOP，即插即用，没有代码侵入性，使用slf4j日志门面打印日志，具体的实现自己引入即可，插件包含两个模块

`easy-validate-request` - 一般在在对外暴露接口的声明包中引用，因为声明包一般都是轻量级，基本不包含实现的

`easy-validate-service` - 是校验的核心包，包含参数校验的实现，可以不单独引用`easy-validate-request`单独使用

spring工程中自动参数校验
-----
不管是桌面级应用、web应用、还是微服务，基本都是都是基于spring构建的了，首先，在spring应用中引入以下pom
```xml
<dependency>
    <groupId>com.rainea.tool</groupId>
    <artifactId>easy-validate-service</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
第二步，如果是单纯的spring工程，需要在xml配置文件中加入 `<aop:aspectj-autoproxy />` 声明来让spring自动为哪些配置@aspectJ切面的bean创建代理，springboot应用已经自动开启了，
然后创建 `ParamValidateAspect` bean到spring容器，并对其进行简单的设置，并注册参数有异常时的返回值处理器，如果方法的返回值并未进行注册，将会抛出 `ParamInvalidException` 异常
```
@Bean
    public ParamValidateAspect paramValidateAspect() {
        ParamValidateAspect aspect = new ParamValidateAspect();
        
        ResultDelegation resultDelegation = aspect.getResultDelegation();
        resultDelegation.register(Result.class, invalidProperty -> 
                new Result(401, "非法参数:" + invalidProperty));
        
        return aspect;
    }
```
第三步，在方法的实现上加上 `com.rainea.tool.easy.validate.annotation.Validate`注解，在方法参数上加上响应的校验注解，比如：
`@NotBlank`、`@Email`等，在实体pojo上需要加 `javax.validation.Valid` 注解，否则实体属性不会进行校验的，注意这些注解都要加在方法实现上，方法声明
上的注解可有可无

比如:
```java
@Data
public class Computer {

    @NotBlank
    private String guid;

    private Double price;
}


//接口
public interface EatService {

    void eat();

    Result buy(@NotBlank String serialNo, @Valid Computer computer);
}


//接口实现
@Service
public class EatServiceImpl implements EatService {

    @Override
    public void eat() {
        System.out.println("eat......");
    }

    /**
      * 如果此时computer.getGuid()为空，方法自动返回new Result(401, "非法参数:guid")，
      * 方法体不会得到执行
      * 
      * @param serialNo
      * @param computer
      * @return 
      */
    @Validate
    @Override
    public Result buy(@NotBlank String serialNo, @Valid Computer computer) {
        System.out.println("buy success");
        return new Result();
    }
}
```

通过静态方法校验
-----
如果不需要通过aop进行自动校验，可以通过插件提供的工具类进行pojo的的校验：
```
ComplexInfo info = new ComplexInfo();
        
FieldResult fieldResult = ParamValidateUtil.validateObject(info);
System.out.println("result： " + JSON.toJSONString(fieldResult));
```
