package com.next.huyao.film.controller.cinema.VO.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandResVO implements Serializable {
    private String brandId;
    private String brandName;
    //被选中（前端显示）
    private String isActive;

}
