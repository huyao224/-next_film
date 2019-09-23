package com.next.huyao.film.common.utils;

public final class ToolUtils {
    private ToolUtils() {
    }
    public static  Boolean isEmpty(String src){
        if(src != null && src.trim().length() > 0){
            return false;
        }
        return true;
    }
    public static  Boolean isNotEmpty(String src){
        if(src == null || src.trim().length() == 0){
            return false;
        }
        return true;
    }
}
