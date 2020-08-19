package com.rainea.tool.easy.validate.util;

import com.alibaba.fastjson.JSON;
import com.rainea.tool.easy.validate.utils.ResultDelegation;
import lombok.Data;
import org.junit.Test;

/**
 * @author liulang
 * @date 2019-12-31
 */
public class ResultTest {

    private class SingleResult {

    }

    @Data
    private class ListResult {
        private String msg;
    }

    @Test
    public void resultException() {
        ResultDelegation resultDelegation = new ResultDelegation();
        Object result = resultDelegation.getResult(SingleResult.class, "guid");
        System.out.println(result.getClass() + "-" + JSON.toJSONString(result));

    }

    @Test
    public void resultType() {
        ResultDelegation resultDelegation = new ResultDelegation();
        resultDelegation.register(ListResult.class, (property) -> {
            ListResult listResult = new ListResult();
            listResult.setMsg("非法参数:" + property);
            return listResult;
        });
        Object result = resultDelegation.getResult(ListResult.class, "guid");
        System.out.println(result.getClass() + "-" + JSON.toJSONString(result));


    }
}
