package com.next.huyao.film.service.user;

import com.next.huyao.film.controller.user.VO.EnrollUserVO;
import com.next.huyao.film.controller.user.VO.UserInfoVO;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import org.springframework.stereotype.Service;

//用户service层接口
@Service("userService")
public interface UserServiceAPI {
    //用户注册
    void userEnroll(EnrollUserVO enrollUserVO) throws CommonServiceException;
    //用户名检查
    Boolean checkUserName(String userName) throws CommonServiceException;
    //用户密码验证
    Boolean userAuth(String userName,String userPwd) throws CommonServiceException;
    //获取用户信息
    UserInfoVO describeUserInfo(String userId)throws CommonServiceException;
    //用户信息修改
    UserInfoVO updateUserInfo(UserInfoVO userInfoVO)throws CommonServiceException;
    //用户ID获取
    String describeUserId(String username)throws CommonServiceException;
}
