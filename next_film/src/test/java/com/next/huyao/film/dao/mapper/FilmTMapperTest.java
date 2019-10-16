package com.next.huyao.film.dao.mapper;

import com.next.huyao.film.controller.film.VO.response.filmdetail.ActorResultVO;
import com.next.huyao.film.controller.film.VO.response.filmdetail.FilmDetailResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmTMapperTest {
    @Autowired
    private  FilmInfoTMapper filmInfoTMapper;
    @Autowired
    private  FilmActorTMapper actorTMapper;
    @Test
    public void describeFilmDetailByFilmId() {
        String filmId = "2";
        FilmDetailResultVO filmDetailResultVO = filmInfoTMapper.describeFilmDetailByFilmId(filmId);
        System.out.println("filmDetailResultVO By id:"+filmDetailResultVO);
    }

    @Test
    public void describeFilmDetailByFilmName() {
        String filmName = "药神";
        FilmDetailResultVO filmDetailResultVO = filmInfoTMapper.describeFilmDetailByFilmName(filmName);
        System.out.println("filmDetailResultVO By name:"+filmDetailResultVO);
    }

    @Test
    public void describeActorsByFilmId() {
        String filmId = "2";
        List<ActorResultVO> actors = actorTMapper.describeActorsByFilmId(filmId);
        actors.stream().forEach(
                (data) -> System.out.println(data)
        );
    }
}