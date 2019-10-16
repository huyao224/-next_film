package com.next.huyao.film.controller.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.next.huyao.film.config.properties.FilmProperties;
import com.next.huyao.film.controller.cinema.VO.*;
import com.next.huyao.film.controller.cinema.VO.condition.AreaResVO;
import com.next.huyao.film.controller.cinema.VO.condition.BrandResVO;
import com.next.huyao.film.controller.cinema.VO.condition.HallTypeResVO;
import com.next.huyao.film.controller.cinema.VO.request.DescribeCinemaRequestVO;
import com.next.huyao.film.controller.cinema.VO.response.FieldInfoRequestVO;
import com.next.huyao.film.controller.common.BaseResponseVO;
import com.next.huyao.film.service.cinema.CinemaServiceAPI;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/cinema")
public class CinemaController {
    @Autowired
    private FilmProperties filmProperties;
    @Autowired
    private CinemaServiceAPI cinemaServiceAPI;

    @ApiOperation(value = "获取场次", notes = "获取场次")
    @ApiImplicitParam(name = "cinemaId",
            value = "入参：影院ID",
            paramType = "query", required = true, dataType = "string")
    @RequestMapping(value = "/getFields",method = RequestMethod.GET)
    public BaseResponseVO getFields(String cinemaId){
        //获取电影院信息
        CinemaDetailVO cinemaDetailVO = cinemaServiceAPI.describeCinemaDetail(cinemaId);
        //获取该电影院的场次和场次电影信息
        List<CinemaFilmVO> cinemaFilmVOS = cinemaServiceAPI.describeFieldAndFilmInfo(cinemaId);
        List<Map<String,CinemaFilmVO>> filmList=Lists.newArrayList();
        cinemaFilmVOS.stream().forEach(
                (film) -> {
                    Map<String,CinemaFilmVO> filmVOMap = Maps.newHashMap();
                    filmVOMap.put("filmInfo",film);
                    filmList.add(filmVOMap);
                }
        );

        Map<String,Object> result = Maps.newHashMap();
        result.put("cinemaInfo",cinemaDetailVO);
        result.put("filmList",filmList);

        return BaseResponseVO.success(filmProperties.getImgPre(),result);
    }

    @RequestMapping(value = "/getFieldInfo",method = RequestMethod.POST)
    public BaseResponseVO getFieldInfo(@RequestBody FieldInfoRequestVO requestVO){
        //获取逻辑层调用结果
        CinemaDetailVO cinemaDetailVO = cinemaServiceAPI.describeCinemaDetail(requestVO.getCinemaId());
        FieldHallInfoVO fieldHallInfoVO = cinemaServiceAPI.describeHallInfoByFieldId(requestVO.getFieldId());
        CinemaFilmInfoVO cinemaFilmInfoVO = cinemaServiceAPI.describeFilmInfoByFieldId(requestVO.getFieldId());

        //组织返回参数
        Map<String,Object> result = Maps.newHashMap();
        result.put("filmInfo",cinemaFilmInfoVO);
        result.put("cinemaInfo",cinemaDetailVO);
        result.put("hallInfo",fieldHallInfoVO);

        return BaseResponseVO.success(filmProperties.getImgPre(),result);
    }

    @RequestMapping(value = "/getCinemas",method = RequestMethod.GET)
    public BaseResponseVO getCinemas(DescribeCinemaRequestVO requestVO){
        Page<CinemaVO> cinemaVOPage = cinemaServiceAPI.describeCinemaInfo(requestVO);

        //组织返回参数
        Map<String,Object> result = Maps.newHashMap();
        result.put("filmInfo",cinemaVOPage.getRecords());

        return  BaseResponseVO.success(cinemaVOPage.getCurrent(),cinemaVOPage.getPages(),filmProperties.getImgPre(),result);
    }

    @RequestMapping(value = "/getCondition",method = RequestMethod.GET)
    public BaseResponseVO getCondition(
            @RequestParam(name = "brandId",required = false,defaultValue = "99")Integer brandId,
            @RequestParam(name = "areaId",required = false,defaultValue = "99")Integer areaId,
            @RequestParam(name = "hallType",required = false,defaultValue = "99")Integer hallType
    ){
        if(!cinemaServiceAPI.checkCondition(brandId,"brandId"))
        {
            brandId = 99;
        }
        if(!cinemaServiceAPI.checkCondition(areaId,"areaId"))
        {
            areaId = 99;
        }
        if(!cinemaServiceAPI.checkCondition(hallType,"hallType"))
        {
            hallType = 99;
        }

        //获取逻辑层结果
        List<BrandResVO> brandResVOS = cinemaServiceAPI.describeBrandConditions(brandId);
        List<AreaResVO> areaResVOS = cinemaServiceAPI.describeAreaConditions(areaId);
        List<HallTypeResVO> hallTypeResVOS = cinemaServiceAPI.describeHallTypeConditions(hallType);

        //组织返回参数
        Map<String,Object> result = Maps.newHashMap();
        result.put("brandList",brandResVOS);
        result.put("areaList",areaResVOS);
        result.put("halltypeList",hallTypeResVOS);

        return BaseResponseVO.success(result);
    }
}
