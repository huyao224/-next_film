package com.next.huyao.film.controller.film.VO.response.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class YearInfoResultVO implements Serializable {
    private String yearId;
    private String yearName;
    private String isActive;
}
