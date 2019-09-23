package com.next.huyao.film.controller.auth.controller.VO;

import com.next.huyao.film.common.utils.ToolUtils;
import com.next.huyao.film.controller.common.BaseResponseVO;
import com.next.huyao.film.controller.common.BaseVO;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import lombok.Data;

@Data
public class AuthRequestVO extends BaseVO {
    private  String username;
    private  String password;

    @Override
    public boolean checkParam() throws ParamErrorException {
        if(ToolUtils.isEmpty(username)){
            throw  new ParamErrorException(400,"用户名不能为空");
        }
        if(ToolUtils.isEmpty(password)){
            throw  new ParamErrorException(400,"用户密码不能为空");
        }
        return true;
    }
}
