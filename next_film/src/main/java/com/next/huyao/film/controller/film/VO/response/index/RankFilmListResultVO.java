package com.next.huyao.film.controller.film.VO.response.index;

import lombok.Data;

import java.io.Serializable;

@Data
public class RankFilmListResultVO implements Serializable {
    private String filmId;
    private String imgAddress;
    private String filmName;

    private String boxNum;//票房排行使用
    private String expectNum;//预期排行使用
    private String score;//TOP排行使用
}
