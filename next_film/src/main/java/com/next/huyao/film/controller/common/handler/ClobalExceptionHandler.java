package com.next.huyao.film.controller.common.handler;

import com.next.huyao.film.controller.common.BaseResponseVO;
import com.next.huyao.film.controller.common.exception.NextfilmException;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ClobalExceptionHandler {

    @ExceptionHandler(NextfilmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseVO nextFilmException(NextfilmException e){
        return  BaseResponseVO.serviceFailed(e.getErrMsg());
    }

    @ExceptionHandler(CommonServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseVO commonServiceException(CommonServiceException e){
        return  BaseResponseVO.serviceFailed(e.getCode(),e.getErrMsg());
    }
    @ExceptionHandler(ParamErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseVO exception(ParamErrorException e){
        return  BaseResponseVO.serviceFailed(e.getCode(),e.getErrMsg());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseVO exception(Exception e){
        return  BaseResponseVO.systemError();
    }
}
