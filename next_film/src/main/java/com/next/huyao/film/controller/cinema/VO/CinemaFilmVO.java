package com.next.huyao.film.controller.cinema.VO;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

//电影院电影实体（电影信息及其 播放场次）
@Data
public class CinemaFilmVO implements Serializable {
    private String  filmId;
    private String  filmName;
    private String  filmLength;
    private String  filmType;
    private String  filmCats;
    private String  actors;
    private String  imgAddress;
    private List<FilmFieldVO> filmFields;
}
