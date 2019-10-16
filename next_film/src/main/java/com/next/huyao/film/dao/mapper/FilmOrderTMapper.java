package com.next.huyao.film.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.huyao.film.controller.order.VO.response.OrderDetailResVO;
import com.next.huyao.film.dao.entity.FilmOrderT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.next.huyao.film.service.order.BO.OrderPriceBO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author huyao
 * @since 2019-10-11
 */
public interface FilmOrderTMapper extends BaseMapper<FilmOrderT> {

    OrderDetailResVO describeOrderDetailById(@Param("orderId")String orderId);

    IPage<OrderDetailResVO> describeOrderDetailByUser(Page<FilmOrderT> page, @Param("useId")String useId);

    OrderPriceBO describeFilmPriceByFieldId(@Param("fieldId")String fieldId);

    String describeSoldSeats(@Param("fieldId")String fieldId);

}
