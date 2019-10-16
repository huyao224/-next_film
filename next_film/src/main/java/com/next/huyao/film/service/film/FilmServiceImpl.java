package com.next.huyao.film.service.film;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.next.huyao.film.common.utils.ToolUtils;
import com.next.huyao.film.common.utils.constant.FilmConditiontype;
import com.next.huyao.film.controller.film.VO.request.DescribeFilmListReqVO;
import com.next.huyao.film.controller.film.VO.response.condition.CatInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.condition.SourceInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.condition.YearInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.ActorResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.FilmDetailResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.ImagesResultVO;
import com.next.huyao.film.controller.film.VO.response.index.BannerInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.index.HotFilmListResultVO;
import com.next.huyao.film.controller.film.VO.response.index.RankFilmListResultVO;
import com.next.huyao.film.controller.film.VO.response.index.SoonFilmListResultVO;
import com.next.huyao.film.dao.entity.*;
import com.next.huyao.film.dao.mapper.*;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//todo film_time有java.sql.SQLFeatureNotSupportedException,问题，推测是Mybatis-plus和Druid的版本问题，对LocalTimeDate类型不支持，导致映射出问题，暂时将FilmInfoT中setFilmTime做了一次date->LocalTimeDate的类型转换
@Service
public class FilmServiceImpl implements FilmServiceAPI{
    @Autowired
    private FilmSourceDictTMapper filmSourceDictTMapper;
    @Autowired
    private FilmYearDictTMapper filmYearDictTMapper;
    @Autowired
    private FilmCatDictTMapper filmCatDictTMapper;

    @Autowired
    private FilmBannerTMapper filmBannerTMapper;

    @Autowired
    private FilmInfoTMapper filmInfoTMapper;
    @Autowired
    private FilmDetailTMapper filmDetailTMapper;

    @Autowired
    private  FilmActorTMapper filmActorTMapper;
    @Autowired
    private  FilmActorRelaTMapper filmActorRelaTMapper;

    /*
        Banner信息获取
     */
    @Override
    public List<BannerInfoResultVO> describeBanners() throws CommonServiceException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_valid","0");

        List<FilmBannerT> banners = filmBannerTMapper.selectList(queryWrapper);
        List<BannerInfoResultVO> result = Lists.newArrayList();
        banners.parallelStream().forEach(
                (banner) ->{
                    BannerInfoResultVO bannerInfoResultVO = new BannerInfoResultVO();
                    bannerInfoResultVO.setBannerId(banner.getUuid()+"");
                    bannerInfoResultVO.setBannerUrl(banner.getBannerUrl());
                    bannerInfoResultVO.setBannerAddress(banner.getBannerAddress());

                    result.add(bannerInfoResultVO);
                });
        return result;
    }

    /*
    获取热映影片
    */
    @Override
    public List<HotFilmListResultVO> describeHotFilms() throws CommonServiceException {
        //默认热映的影片在首页中只查看8条
        Page<FilmInfoT> page = new Page<>(1,8);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","1");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);
        List<HotFilmListResultVO> results = Lists.newArrayList();
        iPage.getRecords().stream().forEach(
                (film) -> {
                    HotFilmListResultVO result = new HotFilmListResultVO();

                    result.setImgAddress(film.getImgAddress());
                    result.setFilmType(film.getFilmType()+"");
                    result.setFilmScore(film.getFilmScore());
                    result.setFilmName(film.getFilmName());
                    result.setFilmId(film.getUuid()+"");
                    results.add(result);
                });

        return results;
    }

    /*
        获取即将上映影片
     */
    @Override
    public List<SoonFilmListResultVO> describeSoonFilms() throws CommonServiceException {
        Page<FilmInfoT> page = new Page<>(1,8);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","2");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);
        List<SoonFilmListResultVO> results = Lists.newArrayList();
        iPage.getRecords().stream().forEach(
                (film) -> {
                    SoonFilmListResultVO result = new SoonFilmListResultVO();

                    result.setImgAddress(film.getImgAddress());
                    result.setFilmType(film.getFilmType()+"");
                    result.setFilmName(film.getFilmName());
                    result.setFilmId(film.getUuid()+"");
                    result.setExpectNum(film.getFilmPresalenum()+"");
                    result.setShowTime(localTimeToStr(film.getFilmTime()));

                    results.add(result);
                });
        return results;
    }

    @Override
    public int describeIndexFilmNum(String filmType) throws CommonServiceException {
        QueryWrapper queryWrapper = new QueryWrapper();
        if("2".equals(filmType)){
            queryWrapper.eq("film_status","2");
        }else{
            queryWrapper.eq("film_status","1");
        }

        return filmInfoTMapper.selectCount(queryWrapper);
    }

    private String localTimeToStr(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormatter.format(localDateTime);
    }

    /*
       票房排行---正在热映电影的票房
    */
    @Override
    public List<RankFilmListResultVO> boxRandFilms() throws CommonServiceException {
        Page<FilmInfoT> page = new Page<>(1,10);
        page.setDesc("film_box_office");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","1");

        List<RankFilmListResultVO> results = Lists.newArrayList();

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);
        iPage.getRecords().stream().forEach(
                (film) -> {
                    RankFilmListResultVO result = new RankFilmListResultVO();

                    result.setScore(film.getFilmScore());
                    result.setImgAddress(film.getImgAddress());
                    result.setFilmName(film.getFilmName());
                    result.setFilmId(film.getUuid()+"");
                    result.setExpectNum(film.getFilmPresalenum()+"");
                    result.setBoxNum(film.getFilmBoxOffice()+"");

                    results.add(result);
                }
        );
        return results;
    }

    /*
       期待排行
    */
    @Override
    public List<RankFilmListResultVO> expectRandFilms() throws CommonServiceException {
        Page<FilmInfoT> page = new Page<>(1,10);
        page.setDesc("film_preSaleNum");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","2");

        List<RankFilmListResultVO> results = Lists.newArrayList();

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);
        iPage.getRecords().stream().forEach(
                (film) -> {
                    RankFilmListResultVO result = new RankFilmListResultVO();

                    result.setScore(film.getFilmScore());
                    result.setImgAddress(film.getImgAddress());
                    result.setFilmName(film.getFilmName());
                    result.setFilmId(film.getUuid()+"");
                    result.setExpectNum(film.getFilmPresalenum()+"");
                    result.setBoxNum(film.getFilmBoxOffice()+"");

                    results.add(result);
                }
        );
        return results;
    }

    @Override
    public List<RankFilmListResultVO> topRandFilms() throws CommonServiceException {
        Page<FilmInfoT> page = new Page<>(1,10);
        page.setDesc("film_score");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","1");

        List<RankFilmListResultVO> results = Lists.newArrayList();

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);
        iPage.getRecords().stream().forEach(
                (film) -> {
                    RankFilmListResultVO result = new RankFilmListResultVO();

                    result.setScore(film.getFilmScore());
                    result.setImgAddress(film.getImgAddress());
                    result.setFilmName(film.getFilmName());
                    result.setFilmId(film.getUuid()+"");
                    result.setExpectNum(film.getFilmPresalenum()+"");
                    result.setBoxNum(film.getFilmBoxOffice()+"");

                    results.add(result);
                }
        );
        return results;
    }

    @Override
    public String checkCondition(String conditionId, FilmConditiontype type) throws CommonServiceException {

        switch (type){
            case SOURCE:
                if("99".equals(conditionId)){
                    return  conditionId;
                }
                FilmSourceDictT filmSourceDictT = filmSourceDictTMapper.selectById(conditionId);
                if(filmSourceDictT != null && ToolUtils.isNotEmpty(filmSourceDictT.getUuid()+"")){
                    return conditionId;
                }else{
                    return "99";
                }
            case YEAR:
                if("99".equals(conditionId)){
                    return  conditionId;
                }
                FilmYearDictT filmYearDictT = filmYearDictTMapper.selectById(conditionId);
                if(filmYearDictT != null && ToolUtils.isNotEmpty(filmYearDictT.getUuid()+"")){
                    return conditionId;
                }else{
                    return "99";
                }
            case CAT:
                if("99".equals(conditionId)){
                    return  conditionId;
                }
                FilmCatDictT filmCatDictT = filmCatDictTMapper.selectById(conditionId);
                if(filmCatDictT != null && ToolUtils.isNotEmpty(filmCatDictT.getUuid()+"")){
                    return conditionId;
                }else{
                    return "99";
                }
            default:
                throw new CommonServiceException(404,"invalid conditionType");
        }
    }


    @Override
    public List<SourceInfoResultVO> describeSourceInfos(String sourceId) throws CommonServiceException {
        String checkedId = checkCondition(sourceId,FilmConditiontype.SOURCE);
        List<FilmSourceDictT> sources = filmSourceDictTMapper.selectList(null);
        List<SourceInfoResultVO> result = sources.stream().map(
                (data) -> {
                    SourceInfoResultVO sourceInfoResultVO = new SourceInfoResultVO();
                    sourceInfoResultVO.setSourceId(data.getUuid()+"");
                    sourceInfoResultVO.setSourceName(data.getShowName());
                    if(checkedId.equals(sourceInfoResultVO.getSourceId())){
                        sourceInfoResultVO.setIsActive("true");
                    }else{
                        sourceInfoResultVO.setIsActive("false");
                    }
                    return sourceInfoResultVO;
                }
        ).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<CatInfoResultVO> describeCatInfos(String catId) throws CommonServiceException {
        String checkedId = checkCondition(catId,FilmConditiontype.CAT);
        List<FilmCatDictT> cats = filmCatDictTMapper.selectList(null);
        List<CatInfoResultVO> result = cats.stream().map(
                (data) -> {
                    CatInfoResultVO catInfoResultVO = new CatInfoResultVO();
                    catInfoResultVO.setCatId(data.getUuid()+"");
                    catInfoResultVO.setCatName(data.getShowName());
                    if(checkedId.equals(catInfoResultVO.getCatId())){
                        catInfoResultVO.setIsActive("true");
                    }else{
                        catInfoResultVO.setIsActive("false");
                    }
                    return catInfoResultVO;
                }
        ).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<YearInfoResultVO> describeYearInfos(String yearId) throws CommonServiceException {
        String checkedId = checkCondition(yearId,FilmConditiontype.YEAR);
        List<FilmYearDictT> years = filmYearDictTMapper.selectList(null);
        List<YearInfoResultVO> result = years.stream().map(
                (data) -> {
                    YearInfoResultVO yearInfoResultVO = new YearInfoResultVO();
                    yearInfoResultVO.setYearId(data.getUuid()+"");
                    yearInfoResultVO.setYearName(data.getShowName());
                    if(checkedId.equals(yearInfoResultVO.getYearId())){
                        yearInfoResultVO.setIsActive("true");
                    }else{
                        yearInfoResultVO.setIsActive("false");
                    }
                    return yearInfoResultVO;
                }
        ).collect(Collectors.toList());
        return result;
    }

    /*
       根据条件获取对应电影列表
    */
    @Override
    public IPage<FilmInfoT> describeFilms(DescribeFilmListReqVO describeFilmListReqVO) throws CommonServiceException {
        Page<FilmInfoT> infoTPage = new Page<FilmInfoT>(Long.parseLong(describeFilmListReqVO.getNowPage()),Long.parseLong(describeFilmListReqVO.getPageSize()));

        //排序方式   1-按热门搜索，2-按时间搜索，3-按评价搜索
        Map<String,String> sortMap = Maps.newHashMap();
        sortMap.put("1","film_preSaleNum");
        sortMap.put("2","film_time");
        sortMap.put("3","film_score");

        //hashmap搜索的时间复杂度为log0
        infoTPage.setDesc(sortMap.get(describeFilmListReqVO.getSortId()));

        QueryWrapper queryWrapper = new QueryWrapper();
        //判断待搜索列表内容   1-正在热映，2-即将上映，3-经典影片
        queryWrapper.eq("film_status",describeFilmListReqVO.getShowType());
        //组织查询条件
        if(!"99".equals(describeFilmListReqVO.getSourceId())){
            queryWrapper.eq("film_source",describeFilmListReqVO.getSourceId());
        }
        if(!"99".equals(describeFilmListReqVO.getYearId())){
            queryWrapper.eq("film_date",describeFilmListReqVO.getYearId());
        }
        if(!"99".equals(describeFilmListReqVO.getCatId())){
            queryWrapper.like("film_cats","#"+describeFilmListReqVO.getCatId()+"#");
        }

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(infoTPage, queryWrapper);

        return iPage;
    }

    /*
       获取电影详情
       searchType -> 0表示按照编号查找，1表示按照名称查找
    */
    @Override
    public FilmDetailResultVO describeFilmDetails(String searchStr, String searchType) throws CommonServiceException {
        FilmDetailResultVO resultVO;
        //0表示按照编号查找，1表示按照名称查找
        if("0".equals(searchType)){
            resultVO = filmInfoTMapper.describeFilmDetailByFilmId(searchStr);
        }else{
            resultVO = filmInfoTMapper.describeFilmDetailByFilmName(searchStr);
        }
        return resultVO;
    }

    /*
      获取影片描述信息
   */
    @Override
    public String describeFilmBiography(String filmId) throws CommonServiceException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_id",filmId);
        List<FilmDetailT> list = filmDetailTMapper.selectList(queryWrapper);
        String biography = "";
        if(list != null && list.size()> 0)
        {
            FilmDetailT filmDetailT = list.get(0);
            biography = filmDetailT.getBiography();
        }

        return biography;
    }

    @Override
    public ImagesResultVO describeFilmImages(String filmId) throws CommonServiceException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_id",filmId);

        ImagesResultVO imagesResultVO = new ImagesResultVO();

        List<FilmDetailT> list = filmDetailTMapper.selectList(queryWrapper);
        if(list != null && list.size()> 0)
        {
            FilmDetailT filmDetailT = list.get(0);
            String[] images = filmDetailT.getFilmImgs().split(",");
            //应该验证image是否存在且为五个

            imagesResultVO.setMainImg(images[0]);
            imagesResultVO.setImg01(images[1]);
            imagesResultVO.setImg02(images[2]);
            imagesResultVO.setImg03(images[3]);
            imagesResultVO.setImg04(images[4]);
        }

        return imagesResultVO;
    }

    @Override
    public ActorResultVO describeDirector(String filmId) throws CommonServiceException {
        //根据filmId获取导演编号
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_id",filmId);
        List<FilmDetailT> list = filmDetailTMapper.selectList(queryWrapper);
        String directorId = "";
        ActorResultVO actorResultVO = new ActorResultVO();

        if(list != null && list.size()> 0)
        {
            FilmDetailT filmDetailT = list.get(0);
            directorId = filmDetailT.getDirectorId()+"";
        }

        //导演编号获取对应的导演信息
        if(ToolUtils.isNotEmpty(directorId)){
            FilmActorT filmActorT = filmActorTMapper.selectById(directorId);
            actorResultVO.setDirectorName(filmActorT.getActorName());
            actorResultVO.setImgAddress(filmActorT.getActorImg());
        }
        return actorResultVO;
    }

    @Override
    public List<ActorResultVO> describeActors(String filmId) throws CommonServiceException {
        List<ActorResultVO> actorResultVOS = filmActorTMapper.describeActorsByFilmId(filmId);
        return actorResultVOS;
    }
}
