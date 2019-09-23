package com.next.huyao.film.controller.user;


import com.next.huyao.film.common.utils.ToolUtils;
import com.next.huyao.film.controller.common.BaseResponseVO;
import com.next.huyao.film.controller.common.TraceUtil;
import com.next.huyao.film.controller.common.exception.NextfilmException;
import com.next.huyao.film.controller.common.exception.ParamErrorException;
import com.next.huyao.film.controller.user.VO.EnrollUserVO;
import com.next.huyao.film.controller.user.VO.UserInfoVO;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import com.next.huyao.film.service.user.UserServiceAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//RestController相当于将Controller和ResponseBody结合
@RestController
@RequestMapping(value = "/user/")
@Api("用户模块相关的API")
public class UserController {
    @Autowired
    private UserServiceAPI userServiceAPI;

    @ApiOperation(value = "用户名重复性验证", notes = "用户名重复性验证")
    @ApiImplicitParam(name = "username",
            value = "入参：待验证用户名",
            paramType = "query", required = true, dataType = "string")
    @RequestMapping(value = "check",method = RequestMethod.POST)
    public BaseResponseVO checkUser(String username) throws CommonServiceException,NextfilmException {
        if(ToolUtils.isEmpty(username)){
            throw new NextfilmException(1,"username不能为空");
        }
        Boolean hasUserName = userServiceAPI.checkUserName(username);
        if(hasUserName){
            return BaseResponseVO.serviceFailed("用户名已存在");
        }else{
            return BaseResponseVO.success();
        }
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public BaseResponseVO register(@RequestBody EnrollUserVO enrollUserVO) throws CommonServiceException,NextfilmException, ParamErrorException {
        //贫血模型与充血模型
        enrollUserVO.checkParam();

        userServiceAPI.userEnroll(enrollUserVO);

        return BaseResponseVO.success();
    }

    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
    public BaseResponseVO describeUserInfo() throws CommonServiceException,ParamErrorException{
        String userId = TraceUtil.getUserId();

        UserInfoVO userInfoVO = userServiceAPI.describeUserInfo(userId);
        userInfoVO.checkParam();

        return BaseResponseVO.success(userInfoVO);
    }

    @RequestMapping(value = "getUserInfo",method = RequestMethod.POST)
    public BaseResponseVO updateUserInfo(@RequestBody UserInfoVO userInfoVO) throws CommonServiceException,ParamErrorException{
        userInfoVO.checkParam();

        UserInfoVO result = userServiceAPI.updateUserInfo(userInfoVO);

        return BaseResponseVO.success(result);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public BaseResponseVO logout() throws CommonServiceException,ParamErrorException{
        String userId = TraceUtil.getUserId();

       /*
       * 1.用户信息一般放入Redis缓存
       * 2.一般退出就是清除缓存
       * */

        return BaseResponseVO.success();
    }
}
