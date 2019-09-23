package com.next.huyao.film.controller.common.exception;

import lombok.Data;

/*
VO参数校验异常
* */
@Data
public class ParamErrorException extends Exception {
    private Integer code;
    private  String errMsg;

    public  ParamErrorException(int code, String errMsg){
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }
}
