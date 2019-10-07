package com.next.huyao.film.dao.mapper;

import com.next.huyao.film.controller.cinema.VO.CinemaFilmInfoVO;
import com.next.huyao.film.controller.cinema.VO.CinemaFilmVO;
import com.next.huyao.film.controller.cinema.VO.FieldHallInfoVO;
import com.next.huyao.film.dao.entity.FilmFieldT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author huyao
 * @since 2019-09-23
 */
public interface FilmFieldTMapper extends BaseMapper<FilmFieldT> {
    //查询一个电影院的所有电影
    List<CinemaFilmVO> describeFieldList(@Param("cinemaId")String cinemaId);
    //查询具体场次的电影信息
    CinemaFilmInfoVO describeFilmInfoByFiledId(@Param("filedId")String filedId);
    //查询具体场次的放映厅信息
    FieldHallInfoVO describeHallInfo(@Param("filedId")String filedId);
}
