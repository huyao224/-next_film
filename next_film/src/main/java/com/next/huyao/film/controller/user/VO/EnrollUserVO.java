package com.next.huyao.film.controller.user.VO;

import com.next.huyao.film.common.utils.ToolUtils;
import com.next.huyao.film.controller.common.BaseVO;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
public class EnrollUserVO extends BaseVO implements Serializable {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;

    @Override
    public boolean checkParam() throws ParamErrorException {
        if(ToolUtils.isEmpty(username)){
            throw  new ParamErrorException(400,"用户名不能为空");
        }
        if(ToolUtils.isEmpty(password)){
            throw  new ParamErrorException(400,"密码不能为空");
        }
        return true;
    }
}
