package com.next.huyao.film.controller.cinema.VO;

import lombok.Data;

import java.io.Serializable;

/*
影院列表实体
* */
@Data
public class CinemaVO implements Serializable {
    private  String uuid;
    private  String cinemaName;
    private  String address;
    private  String minimumPrice;
}
