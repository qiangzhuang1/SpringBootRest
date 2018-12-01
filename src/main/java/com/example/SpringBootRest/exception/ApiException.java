package com.example.SpringBootRest.exception;


import com.example.SpringBootRest.resp.InfoCode;

/**
 * API异常(接口异常)
 */
public class ApiException extends RuntimeException {

    private InfoCode infoCode;

    public ApiException(InfoCode infoCode){
        super(infoCode.getMsg());
        this.infoCode = infoCode;
    }

    public ApiException(InfoCode infoCode,String msg){
        super(msg);
        this.infoCode = infoCode;
    }

    public InfoCode getInfoCode() {
        return infoCode;
    }
}
