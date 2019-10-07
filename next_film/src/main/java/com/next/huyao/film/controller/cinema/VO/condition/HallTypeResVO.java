package com.next.huyao.film.controller.cinema.VO.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallTypeResVO implements Serializable {
    private String halltypeId;
    private String halltypeName;
    //被选中（前端显示）
    private String isActive;

}
