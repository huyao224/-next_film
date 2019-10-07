package com.next.huyao.film.controller.cinema.VO.response;

import com.next.huyao.film.controller.common.BaseVO;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import lombok.Data;

import java.io.Serializable;

@Data
public class FieldInfoRequestVO extends BaseVO implements Serializable {
    private  String cinemaId;
    private  String fieldId;

    @Override
    public boolean checkParam() throws ParamErrorException {
        return false;
    }
}
