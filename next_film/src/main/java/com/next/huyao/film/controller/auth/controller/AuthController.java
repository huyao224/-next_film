package com.next.huyao.film.controller.auth.controller;

import com.next.huyao.film.controller.auth.controller.VO.AuthRequestVO;
import com.next.huyao.film.controller.auth.controller.VO.AuthResponseVO;
import com.next.huyao.film.controller.auth.util.JwtTokenUtil;
import com.next.huyao.film.controller.common.BaseResponseVO;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import com.next.huyao.film.service.user.UserServiceAPI;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserServiceAPI userServiceAPI;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "auth",method = RequestMethod.POST)
    public BaseResponseVO auth(@RequestBody AuthRequestVO authRequestVO) throws ParamErrorException, CommonServiceException {
        authRequestVO.checkParam();
        Boolean isValid = userServiceAPI.userAuth(authRequestVO.getUsername(), authRequestVO.getPassword());

        if(isValid){
            String randomKey = jwtTokenUtil.getRandomKey();
            String token = jwtTokenUtil.generateToken(authRequestVO.getUsername(), authRequestVO.getPassword());
            //TODO return 一个JWT Token
            AuthResponseVO authResponseVO = AuthResponseVO.builder()
                    .randomKey(randomKey)
                    .token(token)
                    .build();
            return BaseResponseVO.success(authResponseVO);
        }else{
            return BaseResponseVO.serviceFailed(1,"用户名或密码不正确");
        }
    }
}
