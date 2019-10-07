package com.next.huyao.film.controller.cinema.VO;

import lombok.Data;

//电影信息实体（某一场次的电影的具体信息）
@Data
public class CinemaFilmInfoVO {
    private String  filmId;
    private String  filmName;
    private String  filmLength;
    private String  filmType;
    private String  filmCats;
    private String  actors;
    private String  imgAddress;
}
