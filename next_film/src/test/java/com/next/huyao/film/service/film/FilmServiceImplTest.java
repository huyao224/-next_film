package com.next.huyao.film.service.film;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.huyao.film.controller.film.VO.request.DescribeFilmListReqVO;
import com.next.huyao.film.controller.film.VO.response.condition.CatInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.ActorResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.FilmDetailResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.ImagesResultVO;
import com.next.huyao.film.controller.film.VO.response.index.BannerInfoResultVO;
import com.next.huyao.film.controller.film.VO.response.index.HotFilmListResultVO;
import com.next.huyao.film.controller.film.VO.response.index.RankFilmListResultVO;
import com.next.huyao.film.controller.film.VO.response.index.SoonFilmListResultVO;
import com.next.huyao.film.dao.entity.FilmInfoT;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmServiceImplTest {
    @Autowired
    private  FilmServiceAPI filmServiceAPI;

    @Test
    public void describeBanners() {
        try {
            List<BannerInfoResultVO> bannerInfoResultVOS = filmServiceAPI.describeBanners();
            bannerInfoResultVOS.stream().forEach(
                    System.out::println
            );
        }catch (CommonServiceException commonSrtviceException){
            commonSrtviceException.printStackTrace();
        }
    }

    @Test
    public void describeHotFilms() throws CommonServiceException {
        List<HotFilmListResultVO> hotFilmListResultVOS = filmServiceAPI.describeHotFilms();
        hotFilmListResultVOS.stream().forEach(
                (data) -> {
                    System.out.println(data);
                }
        );
    }

    @Test
    public void describeSoonFilms() throws CommonServiceException {
        List<SoonFilmListResultVO> soonFilmListResultVOS = filmServiceAPI.describeSoonFilms();
        soonFilmListResultVOS.stream().forEach(
                (data) -> {
                    System.out.println(data);
                }
        );
    }

    @Test
    public void boxRandFilms() throws CommonServiceException {
        List<RankFilmListResultVO> rankFilmListResultVOS = filmServiceAPI.boxRandFilms();
        rankFilmListResultVOS.stream().forEach(
                (data) -> {
                    System.out.println(data);
                }
        );
    }

    @Test
    public void expectRandFilms() {
    }

    @Test
    public void topRandFilms() {
    }

    @Test
    public void checkCondition() {
    }

    @Test
    public void describeCatInfos() throws CommonServiceException {
        List<CatInfoResultVO> catInfoResultVOS = filmServiceAPI.describeCatInfos("1");
        catInfoResultVOS.stream().forEach(
                (data) -> {
                    System.out.println(data);
                }
        );
    }

    @Test
    public void describeSourceInfos() {
    }

    @Test
    public void describeYearInfos() {
    }

    //todo film_time的格式可能有问题
    @Test
    public void describeFilms() throws CommonServiceException {
        DescribeFilmListReqVO reqVO = new DescribeFilmListReqVO();
        IPage<FilmInfoT> filmInfoTIPage = filmServiceAPI.describeFilms(reqVO);
        System.out.println(filmInfoTIPage.getCurrent()+"$$"+filmInfoTIPage.getPages()+"$$"+filmInfoTIPage.getRecords());
    }

    @Test
    public void describeFilmDetails() throws CommonServiceException {
        String searchType = "1";
        String searchStr = "药神";
        FilmDetailResultVO resultVO = filmServiceAPI.describeFilmDetails(searchStr, searchType);
        System.out.println(resultVO );
    }

    @Test
    public void describeFilmBio() throws CommonServiceException {
        System.out.println(filmServiceAPI.describeFilmBiography("2"));
    }

    @Test
    public void describeFilmImages() throws CommonServiceException {
        String filmId = "2";
        ImagesResultVO imagesResultVO = filmServiceAPI.describeFilmImages(filmId);
        System.out.println(imagesResultVO);
    }

    @Test
    public void describeDirector() throws CommonServiceException {
        String filmId = "2";
        ActorResultVO actorResultVO = filmServiceAPI.describeDirector(filmId);
        System.out.println(actorResultVO);
    }

    @Test
    public void describeActors() throws CommonServiceException {
        String filmId = "2";
        List<ActorResultVO> actorResultVOS = filmServiceAPI.describeActors(filmId);
        actorResultVOS.stream().forEach(
                (data) -> {
                    System.out.println(data);
                }
        );
    }
}