package com.next.huyao.film.service.user;

import com.next.huyao.film.controller.user.VO.EnrollUserVO;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceAPI userServiceAPI;
    @Test
    public void userEnroll() throws CommonServiceException {
        EnrollUserVO enrollUserVO =  EnrollUserVO.builder()
                .username("huyao")
                .password("123456")
                .phone("13626223683")
                .address("02-05-06").build();
        userServiceAPI.userEnroll(enrollUserVO);
    }

    @Test
    public void checkUserName() {
    }

    @Test
    public void userAuth() {
    }

    @Test
    public void describeUserInfo() {
    }

    @Test
    public void updateUserInfo() {
    }
}