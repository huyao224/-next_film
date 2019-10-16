package com.next.huyao.film.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.huyao.film.controller.order.VO.response.OrderDetailResVO;
import com.next.huyao.film.dao.entity.FilmOrderT;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmOrderTMapperTest {

    @Autowired
    private FilmOrderTMapper filmOrderTMapper;

    @Test
    public void describeOrderDetailById() {
        String orderId = "415sdf58ew12ds5fe1";
        OrderDetailResVO orderDetailResVO = filmOrderTMapper.describeOrderDetailById(orderId);
        System.out.println(orderDetailResVO);
    }

    @Test
    public void describeOrderDetailByUser() {
        String userId = "1";
        Page<FilmOrderT> page = new Page<>(1,10);
        IPage<OrderDetailResVO> orderDetailResVOIPage = filmOrderTMapper.describeOrderDetailByUser(page, userId);
        orderDetailResVOIPage.getRecords().stream().forEach(
                (data) -> {
                    System.out.println(data);
                }
        );
    }

    @Test
    public void describeFilmPriceByFieldId() {
    }

    @Test
    public void describeSoldSeats() {
    }
}