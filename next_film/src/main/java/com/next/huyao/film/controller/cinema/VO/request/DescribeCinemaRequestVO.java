package com.next.huyao.film.controller.cinema.VO.request;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.next.huyao.film.controller.common.BaseVO;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import com.next.huyao.film.dao.entity.FilmCinemaT;
import lombok.Data;

@Data
public class DescribeCinemaRequestVO extends BaseVO {

    private Integer brandId = 99;
    private Integer hallType = 99;
    private Integer districtId = 99;
    private Integer pageSize = 20;
    private Integer nowPage = 1;
    @Override
    public boolean checkParam() throws ParamErrorException{
        return true;
    }

    /*
    获取当前对象相关的Wrapper对象
    * */
    public QueryWrapper<FilmCinemaT> genWrapper(){
        QueryWrapper<FilmCinemaT> wrapper = new QueryWrapper<>();

        if(this.getBrandId() != null && this.getBrandId() != 99){
            wrapper.eq("brand_id",this.getBrandId());
        }
        if(this.getDistrictId() != null && this.getDistrictId() != 99){
            wrapper.eq("area_id",this.getDistrictId());
        }
        if(this.getHallType() != null && this.getHallType() != 99){
            wrapper.like("hall_ids","#"+this.getHallType()+"#");
        }
        return wrapper;
    }
}
