package com.next.huyao.film.controller.cinema.VO;

import lombok.Data;

import java.io.Serializable;

//播放场次实体
@Data
public class FilmFieldVO implements Serializable {
    private  String fieldId;
    private  String beginTime;
    private  String endTime;
    private  String language;
    private  String hallName;
    private  String price;
}
