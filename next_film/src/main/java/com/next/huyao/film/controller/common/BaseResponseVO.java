package com.next.huyao.film.controller.common;

import lombok.Data;

@Data
public final class BaseResponseVO<T> {
    private  BaseResponseVO(){};
    //返回状态【0-成功，1-业务失败，999-系统异常】
    private  int status;
    //返回信息
    private  String msg;
    //返回数据实体
    private  T data;
    //图片前缀
    private  String imgPre;
    //分页使用
    private  Integer nowPage;
    private  Integer totalPage;

    public  static<T> BaseResponseVO success(){
        BaseResponseVO baseResponseVO =  new BaseResponseVO();
        baseResponseVO.setStatus(0);
        return baseResponseVO;
    }
    public  static<T> BaseResponseVO success(String msg){
        BaseResponseVO baseResponseVO =  new BaseResponseVO();
        baseResponseVO.setStatus(0);
        baseResponseVO.setMsg(msg);
        return baseResponseVO;
    }
    public  static<T> BaseResponseVO success(T data){
        BaseResponseVO baseResponseVO =  new BaseResponseVO();
        baseResponseVO.setStatus(0);
        baseResponseVO.setData(data);
        return baseResponseVO;
    }
    public  static<T> BaseResponseVO success(String imgPre,T data){
        BaseResponseVO baseResponseVO =  new BaseResponseVO();
        baseResponseVO.setStatus(0);
        baseResponseVO.setImgPre(imgPre);
        baseResponseVO.setData(data);
        return baseResponseVO;
    }


    public  static<T> BaseResponseVO noLogin(){
        BaseResponseVO baseResponseVO =  new BaseResponseVO();
        baseResponseVO.setStatus(700);
        baseResponseVO.setMsg("用户需要登录");
        return baseResponseVO;
    }
    public  static<T> BaseResponseVO serviceFailed(int status,String msg){
        BaseResponseVO baseResponseVO =  new BaseResponseVO();
        baseResponseVO.setStatus(status);
        baseResponseVO.setMsg(msg);
        return baseResponseVO;
    }
    public  static<T> BaseResponseVO serviceFailed(String msg){
        BaseResponseVO baseResponseVO =  new BaseResponseVO();
        baseResponseVO.setStatus(1);
        baseResponseVO.setMsg(msg);
        return baseResponseVO;
    }
    public  static<T> BaseResponseVO serviceFailed(String msg,T data){
        BaseResponseVO baseResponseVO =  new BaseResponseVO();
        baseResponseVO.setStatus(1);
        baseResponseVO.setMsg(msg);
        baseResponseVO.setData(data);
        return baseResponseVO;
    }
    public  static<T> BaseResponseVO systemError(){
        BaseResponseVO baseResponseVO =  new BaseResponseVO();
        baseResponseVO.setStatus(999);
        baseResponseVO.setMsg("系统异常，请通知管理员");
        return baseResponseVO;
    }
}
