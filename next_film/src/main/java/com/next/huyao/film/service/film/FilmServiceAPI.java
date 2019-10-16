package com.next.huyao.film.service.film;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.huyao.film.common.utils.constant.FilmConditiontype;
import com.next.huyao.film.controller.film.VO.request.DescribeFilmListReqVO;
import com.next.huyao.film.controller.film.VO.response.condition.CatInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.condition.SourceInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.condition.YearInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.ActorResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.FilmDetailResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.ImagesResultVO;
import com.next.huyao.film.controller.film.VO.response.films.DescribeFilmListResultVO;
import com.next.huyao.film.controller.film.VO.response.index.BannerInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.index.HotFilmListResultVO;
import com.next.huyao.film.controller.film.VO.response.index.RankFilmListResultVO;
import com.next.huyao.film.controller.film.VO.response.index.SoonFilmListResultVO;
import com.next.huyao.film.dao.entity.FilmInfoT;
import com.next.huyao.film.service.common.exception.CommonServiceException;


import java.util.List;

public interface FilmServiceAPI {
    /*
        Banner信息获取
     */
    List<BannerInfoResultVO> describeBanners() throws CommonServiceException;
    /*
        获取热映影片
     */
    List<HotFilmListResultVO> describeHotFilms() throws CommonServiceException;
    /*
        获取即将上映影片
     */
    List<SoonFilmListResultVO> describeSoonFilms() throws CommonServiceException;
    /*
       获取上映或即将上映的影片数量
       1-热映 2-即将上映
    */
    int describeIndexFilmNum(String filmType) throws CommonServiceException;

    /*
       票房排行
    */
    List<RankFilmListResultVO> boxRandFilms() throws CommonServiceException;
    /*
       期待排行
    */
    List<RankFilmListResultVO> expectRandFilms() throws CommonServiceException;
    /*
       Top100排行
    */
    List<RankFilmListResultVO> topRandFilms() throws CommonServiceException;


    /*
       条件有效性验证
    */
    String checkCondition(String conditionId, FilmConditiontype type) throws CommonServiceException;

    /*
       三种条件查询
    */
    List<CatInfoResultVO> describeCatInfos(String catId) throws CommonServiceException;
    List<SourceInfoResultVO> describeSourceInfos(String sourceId) throws CommonServiceException;
    List<YearInfoResultVO> describeYearInfos(String yearId) throws CommonServiceException;

    /*
       根据条件获取对应电影列表
    */
    IPage<FilmInfoT> describeFilms(DescribeFilmListReqVO describeFilmListReqVO) throws CommonServiceException;

    /*
       获取电影详情
       searchType -> 0表示按照编号查找，1表示按照名称查找
    */
    FilmDetailResultVO describeFilmDetails(String searchStr,String searchType) throws CommonServiceException;
    /*
       获取影片描述信息
    */
    String describeFilmBiography(String filmId) throws CommonServiceException;
    /*
       获取影片图片信息
    */
    ImagesResultVO describeFilmImages(String filmId) throws CommonServiceException;
    /*
       获取导演信息
    */
    ActorResultVO describeDirector(String filmId) throws CommonServiceException;
    /*
       获取演员信息
    */
    List<ActorResultVO> describeActors(String filmId) throws CommonServiceException;
}
