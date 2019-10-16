package com.next.huyao.film.dao.mapper;

import com.next.huyao.film.controller.film.VO.response.filmdetail.ActorResultVO;
import com.next.huyao.film.dao.entity.FilmActorT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author huyao
 * @since 2019-10-07
 */
public interface FilmActorTMapper extends BaseMapper<FilmActorT> {
    /*
    根据电影获取演员列表
    * */
    List<ActorResultVO> describeActorsByFilmId(@Param("filmId")String filmId);
}
