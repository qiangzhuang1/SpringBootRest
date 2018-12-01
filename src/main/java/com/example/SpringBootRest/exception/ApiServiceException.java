package com.example.SpringBootRest.exception;


/**
 * Service异常(业务逻辑异常)
 */
public class ApiServiceException extends RuntimeException {

    private Integer infoCode;

    public ApiServiceException(){
    }
    public ApiServiceException(Integer infoCode){
        this.infoCode = infoCode;
    }

    public ApiServiceException(Integer infoCode, String msg){
        super(msg);
        this.infoCode = infoCode;
    }

    public Integer getInfoCode() {
        return infoCode;
    }
}
