package com.next.huyao.film.dao.mapper;

import com.next.huyao.film.controller.cinema.VO.CinemaFilmInfoVO;
import com.next.huyao.film.controller.cinema.VO.CinemaFilmVO;
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
public class FilmFieldTMapperTest {
    @Autowired
    private  FilmFieldTMapper filmFieldTMapper;
    @Test
    public void describeFieldList() {
        List<CinemaFilmVO> cinemaFilmVOS = filmFieldTMapper.describeFieldList("1");
            System.out.println(cinemaFilmVOS);
    }

    @Test
    public void describeFieldList1() {
    }

    @Test
    public void describeFilmInfoByFiledId() {
        CinemaFilmInfoVO cinemaFilmInfoVO = filmFieldTMapper.describeFilmInfoByFiledId("1");
        log.info("获取电影详细信息测试：{}",cinemaFilmInfoVO);
    }

    @Test
    public void describeHallInfo() {
    }
}