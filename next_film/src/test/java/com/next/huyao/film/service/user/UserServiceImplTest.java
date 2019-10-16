package com.next.huyao.film.service.user;

import com.next.huyao.film.controller.user.VO.EnrollUserVO;
import com.next.huyao.film.controller.user.VO.UserInfoVO;
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

    }

    @Test
    public void checkUserName() {
    }

    @Test
    public void userAuth() throws CommonServiceException {
        Boolean aBoolean = userServiceAPI.userAuth("string", "string");
        System.out.println(aBoolean);
    }

    @Test
    public void describeUserInfo() {
    }

    @Test
    public void updateUserInfo() {
    }
}