package com.next.huyao.film.service.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.huyao.film.controller.order.VO.response.OrderDetailResVO;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    OrderServiceAPI orderServiceAPI;
    @Test
    public void checkSeats() throws IOException, CommonServiceException {
        String fieldId = "1";
        String seats = "1,3,5";
        orderServiceAPI.checkSeats(fieldId,seats);
    }

    @Test
    public void checkSoldSeats() throws CommonServiceException {
        String fieldId = "1";
        String seats = "1,3,4";
        orderServiceAPI.checkSoldSeats(fieldId,seats);
    }

    @Test
    public void saveOrder() throws CommonServiceException {
        String field = "1";
        String seatIds = "10,11,12";
        String seatNames= "第二排4座,第二排5座,第二排6座";
        String userId = "1";
        System.out.println(orderServiceAPI.saveOrder(seatIds,seatNames,field,userId));

    }

    @Test
    public void describeInfoByUser() throws CommonServiceException {
        String userId = "1";
        int nowPage = 1;
        int pageSize = 10;
        IPage<OrderDetailResVO> orderDetailResVOIPage = orderServiceAPI.describeInfoByUser(nowPage, pageSize, userId);
        orderDetailResVOIPage.getRecords().stream().forEach(
                (data) ->{
                    System.out.println(data);
                }
        );
    }
}