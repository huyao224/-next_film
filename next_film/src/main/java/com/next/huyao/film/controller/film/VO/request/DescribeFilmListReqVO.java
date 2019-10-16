package com.next.huyao.film.controller.film.VO.request;

import com.next.huyao.film.controller.common.BaseVO;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import lombok.Data;

import java.io.Serializable;

@Data
public class DescribeFilmListReqVO extends BaseVO implements Serializable {
    private String showType = "1";
    private String sortId = "1";
    private String catId = "99";
    private String sourceId = "99";
    private String yearId = "99";
    private String nowPage = "1";
    private String pageSize = "18";

    @Override
    public boolean checkParam() throws ParamErrorException {
        //showType如果不是1,2，3,中的一个，直接抛出异常
        if(!"1".equals(showType) && !"2".equals(showType) && !"3".equals(showType)){
            throw new ParamErrorException(400,"查询类型不正确");
        }
        //sortId如果不是1,2，3,中的一个，直接抛出异常
        if(!"1".equals(sortId) && !"2".equals(sortId) && !"3".equals(sortId)){
            throw new ParamErrorException(400,"排序方式不正确");
        }
        return true;
    }
}
