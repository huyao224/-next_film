package com.next.huyao.film.service.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.huyao.film.controller.cinema.VO.*;
import com.next.huyao.film.controller.cinema.VO.condition.AreaResVO;
import com.next.huyao.film.controller.cinema.VO.condition.BrandResVO;
import com.next.huyao.film.controller.cinema.VO.condition.HallTypeResVO;
import com.next.huyao.film.controller.cinema.VO.request.DescribeCinemaRequestVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CinemaServiceAPI {
    /*
    * 根据条件查询影院列表
    * */
    Page<CinemaVO> describeCinemaInfo(DescribeCinemaRequestVO describeCinemaRequestVO);

    /*
     * 获取查询条件
     * */
    boolean checkCondition(int conditionId,String conditionType);
    List<BrandResVO> describeBrandConditions(int brandId);
    List<AreaResVO> describeAreaConditions(int areaId);
    List<HallTypeResVO> describeHallTypeConditions(int brandId);

    /*
     * 根据影院编号获取影院信息
     * */
    CinemaDetailVO describeCinemaDetail(String cinemaId);

    /*
    * 根据影院编号获取场次信息
    * */
    List<CinemaFilmVO> describeFieldAndFilmInfo(String cinemaId);

    /*
    根据场次编号获取电影信息
    * **/
    CinemaFilmInfoVO describeFilmInfoByFieldId(String fieldId);

    /*
    根据场次编号获取放映厅信息
    * **/
    FieldHallInfoVO  describeHallInfoByFieldId(String fieldId);
}
