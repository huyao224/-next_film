package com.next.huyao.film.controller.user.VO;

import com.next.huyao.film.common.utils.ToolUtils;
import com.next.huyao.film.controller.common.BaseVO;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoVO extends BaseVO implements Serializable {
    private Integer id;
    private Integer uuid;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private int sex;
    private String birthday;
    private String lifeState;
    private String biography;
    private String address;
    private String headAddress;
    private long beginTime;
    private long updateTime;

    public  Integer getUuid(){
        return  this.getId();
    }

    @Override
    public boolean checkParam() throws ParamErrorException {
        if(ToolUtils.isEmpty(username)){
            throw  new ParamErrorException(400,"用户名不能为空");
        }
        return true;
    }
}
