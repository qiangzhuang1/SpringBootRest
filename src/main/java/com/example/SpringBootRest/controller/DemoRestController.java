package com.example.SpringBootRest.controller;

import com.example.SpringBootRest.exception.ApiException;
import com.example.SpringBootRest.resp.InfoCode;
import com.example.SpringBootRest.resp.RestResp;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/demoRest")
public class DemoRestController {

    @RequestMapping(value = "/userLogin")
    public Object userLogin(@RequestParam(value = "userName")String userName,
                            @RequestParam(value = "pwd")String pwd){
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(pwd)){
            return RestResp.build(InfoCode.FAIL,"请求参数不能为空");
            //throw new ApiException(InfoCode.FAIL,"请求参数为空");  两种方式均可
        }
        //TODO 业务逻辑的处理或者调用业务逻辑的方法
        Map<String, Object> map = Maps.newHashMap();
        map.put("userName", userName);
        map.put("pwd", pwd);
        return map;
    }
}