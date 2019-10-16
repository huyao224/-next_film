package com.next.huyao.film.service.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.huyao.film.common.utils.ToolUtils;
import com.next.huyao.film.config.properties.OrderProperties;
import com.next.huyao.film.controller.cinema.VO.CinemaFilmInfoVO;
import com.next.huyao.film.controller.cinema.VO.FieldHallInfoVO;
import com.next.huyao.film.controller.order.VO.response.OrderDetailResVO;
import com.next.huyao.film.dao.entity.FilmOrderT;
import com.next.huyao.film.dao.mapper.FilmOrderTMapper;
import com.next.huyao.film.service.cinema.CinemaServiceAPI;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import com.next.huyao.film.service.order.BO.OrderPriceBO;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderServiceAPI {
    @Autowired
    private FilmOrderTMapper filmOrderTMapper;
    @Autowired
    private CinemaServiceAPI cinemaServiceAPI;

    @Autowired
    private OrderProperties orderProperties;
    /*
    检查座位信息
    --文件信息一般会在分布式的文件存储|对象存储里
    FILE_PATH+seat_address
    * */
    @Override
    public void checkSeats(String fieldId, String seats) throws CommonServiceException, IOException {
        FieldHallInfoVO fieldHallInfoVO = cinemaServiceAPI.describeHallInfoByFieldId(fieldId);
        if(fieldHallInfoVO == null || ToolUtils.isEmpty(fieldHallInfoVO.getSoldSeats())){
            throw  new CommonServiceException(404,"场次编号不正确");
        }
        String seatsPath = orderProperties.getFilePathPre() + fieldHallInfoVO.getSeatFile();
        //lombok关闭文件流注解  Cleanup
        @Cleanup
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(seatsPath)
        );
        StringBuffer  stringBuffer =new StringBuffer();
        String temp = new String();
        while((temp = bufferedReader.readLine()) != null){
            stringBuffer.append(temp);
        }
        JSONObject jsonObject = JSON.parseObject(stringBuffer.toString());
        //获取ids节点
        String idsStr = jsonObject.getString("ids");
        /*
            用户购买：3,11,12
            ids:1-24
        */
        List<String> idsList = Arrays.asList(idsStr.split(","));
        String[] seatArr = seats.split(",");
        for(String seatId : seatArr){
            boolean contains = idsList.contains(seatId);
            if(!contains){
                throw  new CommonServiceException(500,"传入的座位信息有误");
            }
        }
    }

    /*
    检查待售卖的座位是否有已售座位信息
    * */
    @Override
    public void checkSoldSeats(String fieldId, String seats) throws CommonServiceException {
        String soldSeats = filmOrderTMapper.describeSoldSeats(fieldId);
         /*
            用户购买：seats
            已售信息:soldSeats
        */
        List<String> soldSeatsList = Arrays.asList(soldSeats.split(","));
        String[] seatArr = seats.split(",");
        for(String seatId : seatArr){
            boolean contains = soldSeatsList.contains(seatId);
            if(contains){
                throw  new CommonServiceException(500,seatId+"为已售座位，不能重复销售");
            }
        }
    }

    @Override
    public OrderDetailResVO saveOrder(String seatIds, String seatNames, String fieldId, String userId) throws CommonServiceException {

        String uuid = UUID.randomUUID().toString().replace("-","");

        OrderPriceBO orderPriceBO = filmOrderTMapper.describeFilmPriceByFieldId(fieldId);
        //单个座位的票价
        double filmPrice = orderPriceBO.getFilmPrice();
        //销售的座位数->票数
        int seatNum = seatIds.split(",").length;
        //todo  计算后总票件-->double有问题
        double totalPrice =getTotalPrice(filmPrice,seatNum);

        //获取filmId
        CinemaFilmInfoVO cinemaFilmInfoVO = cinemaServiceAPI.describeFilmInfoByFieldId(fieldId);

        FilmOrderT filmOrderT = new FilmOrderT();
        filmOrderT.setUuid(uuid);
        filmOrderT.setSeatsName(seatNames);
        filmOrderT.setSeatsIds(seatIds);
        filmOrderT.setOrderUser(Integer.parseInt(userId));
        filmOrderT.setOrderPrice(totalPrice);
        filmOrderT.setFilmPrice(filmPrice);
        filmOrderT.setFilmId(Integer.parseInt(cinemaFilmInfoVO.getFilmId()));
        filmOrderT.setFieldId(Integer.parseInt(fieldId));
        filmOrderT.setCinemaId(Integer.parseInt(orderPriceBO.getCinemaId()));

        filmOrderTMapper.insert(filmOrderT);
        OrderDetailResVO orderDetailResVO = filmOrderTMapper.describeOrderDetailById(uuid);

        return orderDetailResVO;
    }

    /*
    double类型是用于科学计数的，本身是二进制，转化为10进制是会产生进度问题
    例 2.3*0.4=0.919999999...
    java提供了专门用于精度计算的类：BigDecimal
    * */
    private double getTotalPrice(double filmPrice,int seatNum){
        BigDecimal b1 = new BigDecimal(filmPrice);
        BigDecimal b2 = new BigDecimal(seatNum);

        BigDecimal bigDecimal = b1.multiply(b2);

        //小数点后去两位，以四舍五入的方式实现
        BigDecimal result = bigDecimal.setScale(2, RoundingMode.HALF_UP);

        return  result.doubleValue();
    }

    /*
    根据用户编号获取用户订单信息列表
    * */
    @Override
    public IPage<OrderDetailResVO> describeInfoByUser(int nowPage,int pageSize,String userId) throws CommonServiceException {
        Page<FilmOrderT> page = new Page<>(nowPage,pageSize);
        return filmOrderTMapper.describeOrderDetailByUser(page,userId);

    }
}
