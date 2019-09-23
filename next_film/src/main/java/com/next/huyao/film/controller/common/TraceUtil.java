package com.next.huyao.film.controller.common;

import java.security.PrivateKey;

public final class TraceUtil {
    private TraceUtil(){};

    private final static  ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static  void  initThread(String userId){
        threadLocal.set(userId);
    }
    public static String getUserId(){
        return threadLocal.get();
    }

}
