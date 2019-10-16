package com.next.huyao.film.dao.mapper;

import com.next.huyao.film.controller.film.VO.response.filmdetail.FilmDetailResultVO;
import com.next.huyao.film.dao.entity.FilmInfoT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author huyao
 * @since 2019-10-05
 */
public interface FilmInfoTMapper extends BaseMapper<FilmInfoT> {
    /*
    根据电影ID获取电影详情
    * */
    FilmDetailResultVO describeFilmDetailByFilmId(@Param("filmId")String filmId);

    /*
    根据电影名称获取电影详情 -- 模糊匹配
    * */
    FilmDetailResultVO describeFilmDetailByFilmName(@Param("filmName")String filmName);
}
