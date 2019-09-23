package com.next.huyao.film.service.common.exception;

import lombok.Data;


public class DataTranctionException extends Exception{
    private int code;
    private String errMsg;

    public DataTranctionException(int code,String errMsg){
        super();
        this.code=code;
        this.errMsg=errMsg;
    }
}
