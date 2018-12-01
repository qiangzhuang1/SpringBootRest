package com.example.SpringBootRest.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.SpringBootRest.resp.InfoCode;
import com.example.SpringBootRest.resp.RestResp;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;


/**
 * 统一返回值处理
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {
    private static Logger logger = LoggerFactory.getLogger(ResponseAdvice.class);

    private static final String API_URL = "/api";

    private static final String REQUEST_ID = "request-id";
    private static final String OPERATION_COST_TIME = "cost-time";
    private static final String REQUEST_TIME = "request-time";
    private static final String RESPONSE_URL = "request-url";
    private static final String ENTITY = "entity";
    private static final String STATUS = "status";

    SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.DisableCircularReferenceDetect};

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest req = null;
        ServletServerHttpResponse resp = null;

        if (serverHttpRequest instanceof ServletServerHttpRequest){
            req = (ServletServerHttpRequest)serverHttpRequest;
        }
        if (serverHttpResponse instanceof ServletServerHttpResponse){
            resp = (ServletServerHttpResponse)serverHttpResponse;
        }
        String url = req.getServletRequest().getServletPath();
        if (StringUtils.isNotBlank(url)&&! isApi(url)){
            return o;
        }
        if (o instanceof RestResp){
            logger(req,resp,o);
            return o;
        }
        logger(req,resp,o);
        return new RestResp(InfoCode.SUCCESS,o);
    }

    private void logger(ServletServerHttpRequest req, ServletServerHttpResponse resp,Object body){
        Long requestId = (Long) req.getServletRequest().getAttribute(REQUEST_ID);
        Long reqTime = (Long) req.getServletRequest().getAttribute(REQUEST_TIME);
        Long costTime = System.currentTimeMillis()-reqTime;
        Map<String,Object> map = Maps.newHashMap();
        map.put(STATUS,resp.getServletResponse().getStatus());
        map.put(OPERATION_COST_TIME,costTime);
        map.put(REQUEST_ID,requestId);
        StringBuffer url = req.getServletRequest().getRequestURL();
        map.put(RESPONSE_URL,url);
        map.put(ENTITY,body);
        logger.info(JSON.toJSONString(map,features));
    }

    public static Boolean isApi(String path){
        if (StringUtils.isBlank(path)){
            return false;
        }
        if (StringUtils.startsWith(path,API_URL)){
            return true;
        }
        return false;
    }

}
