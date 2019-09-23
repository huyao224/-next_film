package com.next.huyao.film.controller.auth.controller.VO;

import com.next.huyao.film.controller.common.BaseVO;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponseVO extends BaseVO {
    private String randomKey;
    private String token;

    @Override
    public boolean checkParam() throws ParamErrorException{
        return true;
    }
}
