package com.example.SpringBootRest.advice;

import com.example.SpringBootRest.exception.ApiException;
import com.example.SpringBootRest.resp.InfoCode;
import com.example.SpringBootRest.resp.RestResp;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 */
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public RestResp errorHandler(Exception ex) {
        if (ex instanceof ApiException) {
            return RestResp.build(((ApiException) ex).getInfoCode(), ex.getMessage());
        }
        return RestResp.build(InfoCode.SERVICE_UNAVAILABLE, ex.getMessage());
    }
}
