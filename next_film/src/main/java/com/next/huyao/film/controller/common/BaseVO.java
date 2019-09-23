package com.next.huyao.film.controller.common;

import com.next.huyao.film.controller.common.exception.ParamErrorException;

public abstract class BaseVO {
    public abstract boolean checkParam() throws ParamErrorException;
}
