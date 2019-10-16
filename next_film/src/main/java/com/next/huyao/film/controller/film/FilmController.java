package com.next.huyao.film.controller.film;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.next.huyao.film.common.utils.constant.FilmConditiontype;
import com.next.huyao.film.config.properties.FilmProperties;
import com.next.huyao.film.controller.common.BaseResponseVO;
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
import com.next.huyao.film.dao.entity.FilmInfoT;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import com.next.huyao.film.service.film.FilmServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmServiceAPI filmServiceAPI;
    @Autowired
    private FilmProperties filmProperties;
    /*
    获取首页信息
    * */
    @RequestMapping(value = "/getIndex",method = RequestMethod.GET)
    public BaseResponseVO describeIndex() throws CommonServiceException {
        Map<String,Object> resultMap = Maps.newHashMap();

        //获取banners
        List<BannerInfoResultVO> banners = filmServiceAPI.describeBanners();
        resultMap.put("banners",banners);

        //hotFilms
        List<HotFilmListResultVO> hotFilmListResultVOS = filmServiceAPI.describeHotFilms();
        int hotFilmNum = filmServiceAPI.describeIndexFilmNum("1");
        Map<String,Object> hotFilmMap = Maps.newHashMap();
        hotFilmMap.put("filmNum",hotFilmNum);
        hotFilmMap.put("filmInfo",hotFilmListResultVOS);

        resultMap.put("hotFilms",hotFilmMap);

        //soonFilms
        List<SoonFilmListResultVO> soonFilmListResultVOS = filmServiceAPI.describeSoonFilms();
        int soonFilmNum = filmServiceAPI.describeIndexFilmNum("2");
        Map<String,Object> soonFilmMap = Maps.newHashMap();
        soonFilmMap.put("filmNum",soonFilmNum);
        soonFilmMap.put("filmInfo",soonFilmListResultVOS);

        resultMap.put("soonFilms",soonFilmMap);

        //boxRanking
        List<RankFilmListResultVO> boxRanking = filmServiceAPI.boxRandFilms();
        resultMap.put("boxRanking",boxRanking);

        //expectRanking
        List<RankFilmListResultVO> expectRanking = filmServiceAPI.expectRandFilms();
        resultMap.put("expectRanking",expectRanking);

        //top100
        List<RankFilmListResultVO> topRanking = filmServiceAPI.topRandFilms();
        resultMap.put("top100",topRanking);

        return BaseResponseVO.success(filmProperties.getImgPre(),resultMap);
    }

    /*
    影片条件列表查询
    * */
    @RequestMapping(value = "/getConditionList",method = RequestMethod.GET)
    public BaseResponseVO getConditionList(
            @RequestParam(name = "catId",required = false,defaultValue = "99") String catId,
            @RequestParam(name = "sourceId",required = false,defaultValue = "99") String sourceId,
            @RequestParam(name = "yearId",required = false,defaultValue = "99") String yearId
    ) throws CommonServiceException {
        catId = filmServiceAPI.checkCondition(catId, FilmConditiontype.CAT);
        sourceId = filmServiceAPI.checkCondition(catId, FilmConditiontype.SOURCE);
        yearId = filmServiceAPI.checkCondition(catId, FilmConditiontype.YEAR);

        Map<String,Object> resultMap = Maps.newHashMap();

        List<CatInfoResultVO> catInfoResultVOS = filmServiceAPI.describeCatInfos(catId);
        List<SourceInfoResultVO> sourceInfoResultVOS = filmServiceAPI.describeSourceInfos(sourceId);
        List<YearInfoResultVO> yearInfoResultVOS = filmServiceAPI.describeYearInfos(yearId);

        resultMap.put("catInfo",catInfoResultVOS);
        resultMap.put("sourceInfo",sourceInfoResultVOS);
        resultMap.put("yearInfo",yearInfoResultVOS);

        return BaseResponseVO.success(resultMap);
    }

    /*
    影片查询
    * */
    @RequestMapping(value = "/getFilms",method = RequestMethod.GET)
    public BaseResponseVO getFilms(DescribeFilmListReqVO describeFilmListReqVO) throws CommonServiceException {
        IPage<FilmInfoT> page = filmServiceAPI.describeFilms(describeFilmListReqVO);

        return BaseResponseVO.success(page.getCurrent(),page.getTotal(),filmProperties.getImgPre(),page.getRecords());
    }

    /*
    影片详情查询
    * */
    @RequestMapping(value = "/films/{searchStr}",method = RequestMethod.GET)
    public BaseResponseVO describeFilmDetail(@PathVariable(name = "searchStr") String searchStr,String searchType) throws CommonServiceException {
        //根据查询条件获取filmId
        FilmDetailResultVO resultVO = filmServiceAPI.describeFilmDetails(searchStr, searchType);
        String filmId = resultVO.getFilmId();
        //biography
        String biography = filmServiceAPI.describeFilmBiography(filmId);
        //actors
        Map<String,Object> actors = Maps.newHashMap();

        ActorResultVO director = filmServiceAPI.describeDirector(filmId);
        List<ActorResultVO> actorResultVOS = filmServiceAPI.describeActors(filmId);
        actors.put("director",director);
        actors.put("actors",actorResultVOS);

        //images
        ImagesResultVO imagesResultVO = filmServiceAPI.describeFilmImages(filmId);

        resultVO.getInfo04().put("biography",biography);
        resultVO.getInfo04().put("actors",actors);
        resultVO.setImgs(imagesResultVO);

        return BaseResponseVO.success(filmProperties.getImgPre(),resultVO);
    }
}
