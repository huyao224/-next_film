package com.next.huyao.film.service.order.BO;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPriceBO implements Serializable {
    private String cinemaId;
    private Double filmPrice;
}
