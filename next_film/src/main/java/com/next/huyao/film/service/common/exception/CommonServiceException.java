package com.next.huyao.film.service.common.exception;

import lombok.Data;

@Data
public class CommonServiceException extends Exception{
    private int code;
    private String errMsg;

    public CommonServiceException(int code, String errMsg){
        super();
        this.code=code;
        this.errMsg=errMsg;
    }
}
