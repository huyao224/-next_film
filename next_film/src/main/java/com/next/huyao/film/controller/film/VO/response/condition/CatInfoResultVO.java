package com.next.huyao.film.controller.film.VO.response.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatInfoResultVO implements Serializable {
    private String catId;
    private String catName;
    private String isActive;
}
