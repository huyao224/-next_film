package com.next.huyao.film.service.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.huyao.film.controller.order.VO.response.OrderDetailResVO;
import com.next.huyao.film.service.common.exception.CommonServiceException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface OrderServiceAPI {

    /*
    检查座位是否符合现状
    * */
    void checkSeats(String fieldId,String seats) throws CommonServiceException, IOException;

    /*
    检查座位是否为已售座位
    * */
    void checkSoldSeats(String fieldId,String seats) throws CommonServiceException;

    /*
    保存订单信息
    * */
    OrderDetailResVO saveOrder(String seatIds,String seatNames,String fieldId,String userId) throws CommonServiceException;

    /*
    根据用户编号，获取该用户给购买过的影票信息
    * */
    IPage<OrderDetailResVO> describeInfoByUser(int nowPage,int pageSize,String userId) throws CommonServiceException;
}
