package com.next.huyao.film.service.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.huyao.film.controller.cinema.VO.CinemaVO;
import com.next.huyao.film.controller.cinema.VO.condition.AreaResVO;
import com.next.huyao.film.controller.cinema.VO.condition.BrandResVO;
import com.next.huyao.film.controller.cinema.VO.condition.HallTypeResVO;
import com.next.huyao.film.controller.cinema.VO.request.DescribeCinemaRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CinemaServiceImplTest {
    @Autowired
    private  CinemaServiceAPI cinemaServiceAPI;
    @Test
    public void describeCinemaInfo() {
        DescribeCinemaRequestVO describeCinemaRequestVO = new DescribeCinemaRequestVO();
        describeCinemaRequestVO.setBrandId(1);
        describeCinemaRequestVO.setDistrictId(1);
        describeCinemaRequestVO.setHallType(1);
        Page<CinemaVO> page = cinemaServiceAPI.describeCinemaInfo(describeCinemaRequestVO);
        log.info("获取影院信息测试：{}{}{}{}",page.getCurrent(),
                page.getPages(),page.getTotal(),page.getRecords());
    }

    @Test
    public void describeBrandConditions() {
        List<BrandResVO> brandResVOS = cinemaServiceAPI.describeBrandConditions(1);
        brandResVOS.stream().forEach(
                data -> System.out.println(data)
        );
    }

    @Test
    public void describeAreaConditions() {
        List<AreaResVO> areaResVOS = cinemaServiceAPI.describeAreaConditions(1);
        areaResVOS.stream().forEach(
                data -> System.out.println(data)
        );
    }

    @Test
    public void describeHallTypeConditions() {
        List<HallTypeResVO> hallTypeResVOS = cinemaServiceAPI.describeHallTypeConditions(1);
        hallTypeResVOS.stream().forEach(
                data -> System.out.println(data)
        );
    }

    @Test
    public void describeCinemaDetail() {
        System.out.println(cinemaServiceAPI.describeCinemaDetail("1"));
    }

    @Test
    public void describeFieldAndFilmInfo() {
        System.out.println(cinemaServiceAPI.describeFieldAndFilmInfo("1"));
    }

    @Test
    public void describeFilmInfoByFieldId() {
        System.out.println(cinemaServiceAPI.describeFilmInfoByFieldId("1"));
    }

    @Test
    public void describeHallInfoByFieldId() {
        System.out.println(cinemaServiceAPI.describeHallInfoByFieldId("1"));
    }
}