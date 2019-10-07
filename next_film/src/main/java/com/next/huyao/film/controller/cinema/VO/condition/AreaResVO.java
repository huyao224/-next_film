package com.next.huyao.film.controller.cinema.VO.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class AreaResVO implements Serializable {
    private String areaId;
    private String areaName;
    //被选中（前端显示）
    private String isActive;

}
