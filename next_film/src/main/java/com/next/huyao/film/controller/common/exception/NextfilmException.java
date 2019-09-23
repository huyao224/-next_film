package com.next.huyao.film.controller.common.exception;

import lombok.Data;

@Data
public class NextfilmException extends Exception{
    private Integer code;
    private  String errMsg;

    public  NextfilmException(int code, String errMsg){
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }
}
