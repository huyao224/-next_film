package com.next.huyao.film.service.cinema;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.next.huyao.film.controller.cinema.VO.*;
import com.next.huyao.film.controller.cinema.VO.condition.AreaResVO;
import com.next.huyao.film.controller.cinema.VO.condition.BrandResVO;
import com.next.huyao.film.controller.cinema.VO.condition.HallTypeResVO;
import com.next.huyao.film.controller.cinema.VO.request.DescribeCinemaRequestVO;
import com.next.huyao.film.dao.entity.FilmAreaDictT;
import com.next.huyao.film.dao.entity.FilmBrandDictT;
import com.next.huyao.film.dao.entity.FilmCinemaT;
import com.next.huyao.film.dao.entity.FilmHallDictT;
import com.next.huyao.film.dao.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaServiceAPI{

    @Autowired
    private FilmFieldTMapper filmFieldTMapper;
    @Autowired
    private FilmCinemaTMapper filmCinemaTMapper;
    @Autowired
    private FilmHallFilmInfoTMapper filmHallFilmInfoTMapper;

    @Autowired
    private FilmAreaDictTMapper filmAreaDictTMapper;
    @Autowired
    private FilmHallDictTMapper filmHallDictTMapper;
    @Autowired
    private FilmBrandDictTMapper filmBrandDictTMapper;

    @Override
    public Page<CinemaVO> describeCinemaInfo(DescribeCinemaRequestVO describeCinemaRequestVO) {
        //组织分页对象
        Page<FilmCinemaT> page =
                new Page<>(describeCinemaRequestVO.getNowPage(),
                        describeCinemaRequestVO.getPageSize());
        //组织查询条件
        QueryWrapper<FilmCinemaT> filmCinemaTQueryWrapper =
                describeCinemaRequestVO.genWrapper();
        //获取数据库返回
        IPage<FilmCinemaT> filmCinemaTIPage = filmCinemaTMapper.selectPage(page, filmCinemaTQueryWrapper);
        //组织返回值
        Page<CinemaVO> cinemaVOPage = new Page<>(describeCinemaRequestVO.getNowPage(),
                describeCinemaRequestVO.getPageSize());

        cinemaVOPage.setTotal(page.getTotal());
        //将数据实体转换为表现层数据
        List<CinemaVO> cinemas = filmCinemaTIPage.getRecords().stream()
                .map((data) -> {
                    //数据转换
                    CinemaVO cinemaVO = new CinemaVO();
                    cinemaVO.setUuid(data.getUuid()+"");
                    cinemaVO.setCinemaName(data.getCinemaName());
                    cinemaVO.setAddress(data.getCinemaAddress());
                    cinemaVO.setMinimumPrice(data.getMinimumPrice()+"");
                    return cinemaVO;
                }).collect(Collectors.toList());
        cinemaVOPage.setRecords(cinemas);
        return cinemaVOPage;
    }

    //将三个条件的查询抽成一个方法
    @Override
    public boolean checkCondition(int conditionId,String conditionType){
        //conditionId
        //如果无效，则将conditionId=99，并将conditionId=99的isActive设置为true
        switch (conditionType){
            case "brand":
                FilmBrandDictT filmBrandDictT = filmBrandDictTMapper.selectById(conditionId);
                if(filmBrandDictT != null && filmBrandDictT.getUuid() != null){
                    return true;
                }else{
                    return false;
                }
            case "area":
                FilmAreaDictT filmAreaDictT = filmAreaDictTMapper.selectById(conditionId);
                if(filmAreaDictT != null && filmAreaDictT.getUuid() != null){
                    return true;
                }else{
                    return false;
                }
            case "halltype":
                FilmHallDictT filmHallDictT = filmHallDictTMapper.selectById(conditionId);
                if(filmHallDictT != null && filmHallDictT.getUuid() != null){
                    return true;
                }else{
                    return false;
                }
        }
        return false;
    }

    @Override
    public List<BrandResVO> describeBrandConditions(int brandId) {
        //验证brandId是否有效
        //如果无效，则将brandId=99，并将brandId=99的isActive设置为true

        //获取对应的品牌列表
        List<FilmBrandDictT> brands = filmBrandDictTMapper.selectList(null);
        List<BrandResVO> result = brands.stream().map(
                (data) ->{
                    BrandResVO brandResVO = new BrandResVO();
                    if (brandId == data.getUuid())
                    {
                        brandResVO.setIsActive("true");
                    }else{
                        brandResVO.setIsActive("false");
                    }
                    brandResVO.setBrandId(data.getUuid()+"");
                    brandResVO.setBrandName(data.getShowName());
                    return  brandResVO;
                }
        ).collect(Collectors.toList());
        //将能匹配的brandId对应的isActive设置为true
        return result;
    }

    @Override
    public List<AreaResVO> describeAreaConditions(int areaId) {
        //获取对应的地区列表
        List<FilmAreaDictT> areas = filmAreaDictTMapper.selectList(null);
        List<AreaResVO> result = areas.stream().map(
                (data) ->{
                    AreaResVO areaResVO = new AreaResVO();
                    if (areaId == data.getUuid())
                    {
                        areaResVO.setIsActive("true");
                    }else{
                        areaResVO.setIsActive("false");
                    }
                    areaResVO.setAreaId(data.getUuid()+"");
                    areaResVO.setAreaName(data.getShowName());
                    return  areaResVO;
                }
        ).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<HallTypeResVO> describeHallTypeConditions(int brandId) {
        //获取对应的展厅列表
        List<FilmHallDictT> halls = filmHallDictTMapper.selectList(null);
        List<HallTypeResVO> result = halls.stream().map(
                (data) ->{
                    HallTypeResVO hallTypeResVO = new HallTypeResVO();
                    if (brandId == data.getUuid())
                    {
                        hallTypeResVO.setIsActive("true");
                    }else{
                        hallTypeResVO.setIsActive("false");
                    }
                    hallTypeResVO.setHalltypeId(data.getUuid()+"");
                    hallTypeResVO.setHalltypeName(data.getShowName());
                    return  hallTypeResVO;
                }
        ).collect(Collectors.toList());
        return result;
    }

    @Override
    public CinemaDetailVO describeCinemaDetail(String cinemaId) {
        FilmCinemaT filmCinemaT = filmCinemaTMapper.selectById(cinemaId);
        CinemaDetailVO cinemaDetailVO =
                CinemaDetailVO.builder()
                .cinemaAdress(filmCinemaT.getCinemaAddress())
                .cinemaId(filmCinemaT.getUuid()+"")
                .cinemaName(filmCinemaT.getCinemaName())
                .cinemaPhone(filmCinemaT.getCinemaPhone())
                .imgUrl(filmCinemaT.getImgAddress())
                .build();
        return cinemaDetailVO;
    }

    @Override
    public List<CinemaFilmVO> describeFieldAndFilmInfo(String cinemaId) {
        //确认cinemaId是否有效
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("uuid",cinemaId);
        Integer isNull = filmCinemaTMapper.selectCount(queryWrapper);
        if(isNull == 0){
            return  Lists.newArrayList();
        }
        return filmFieldTMapper.describeFieldList(cinemaId);
    }

    @Override
    public CinemaFilmInfoVO describeFilmInfoByFieldId(String fieldId) {

        return filmFieldTMapper.describeFilmInfoByFiledId(fieldId);
    }

    @Override
    public FieldHallInfoVO describeHallInfoByFieldId(String fieldId) {
        return filmFieldTMapper.describeHallInfo(fieldId);
    }
}
