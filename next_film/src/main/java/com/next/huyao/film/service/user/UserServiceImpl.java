package com.next.huyao.film.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.next.huyao.film.common.utils.MD5Util;
import com.next.huyao.film.common.utils.ToolUtils;
import com.next.huyao.film.controller.user.VO.EnrollUserVO;
import com.next.huyao.film.controller.user.VO.UserInfoVO;
import com.next.huyao.film.dao.entity.NextUserT;
import com.next.huyao.film.dao.mapper.NextUserTMapper;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserServiceAPI {
    @Autowired
    NextUserTMapper nextUserTMapper;

    @Override
    public void userEnroll(EnrollUserVO enrollUserVO) throws CommonServiceException {
        //没有思路时，先用注释写流程

        //校验（逻辑层的校验一般是业务上的校验，例如电话有没有重复）

        //EnrollUserVO->NextUser
        NextUserT nextUserT = new NextUserT();
        //BeanUtils:spring工具类，通过反射设置相同名的值
        //eamil adress phone复制
        BeanUtils.copyProperties(enrollUserVO,nextUserT);
        nextUserT.setUserName(enrollUserVO.getUsername());
        nextUserT.setUserPwd(MD5Util.encrypt(enrollUserVO.getPassword()));

        //数据插入
        int isSuccess = nextUserTMapper.insert(nextUserT);

        //判断插入是否成功
        if(isSuccess!=1){
            throw new CommonServiceException(501,"用户更新失败");
        }
    }

    @Override
    public Boolean checkUserName(String userName) throws CommonServiceException {
//        Optional.ofNullable(userName).orElseThrow(new CommonServiceException());

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userName);

        Integer hasUserName = nextUserTMapper.selectCount(queryWrapper);
        return hasUserName == 0 ? false : true;
    }

    @Override
    public Boolean userAuth(String userName, String userPwd) throws CommonServiceException {
        if(ToolUtils.isEmpty(userName) || ToolUtils.isEmpty(userPwd)){
            throw new CommonServiceException(400,"用户验证失败");
        }
        //1.判断用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userName);
        List<NextUserT> list = nextUserTMapper.selectList(queryWrapper);
        //2.如果存在，则判断密码是否正确
        if(list.size()==0){
            return false;
        }else{
            //2.如果存在，则判断密码是否正确
            NextUserT nextUserT=list.get(0);
            if(MD5Util.encrypt(userPwd).equals(nextUserT.getUserPwd())){
                return  true;
            }
        }
        //3.对于用户密码MD5加密，然后判断是否相等
        return false;
    }

    @Override
    public UserInfoVO describeUserInfo(String userId) throws CommonServiceException {
        NextUserT nextUserT=nextUserTMapper.selectById(userId);
        if(nextUserT != null && nextUserT.getUuid() != null){
            UserInfoVO userInfoVO =userToInfoVO(nextUserT);
            return  userInfoVO;
        }else{
            throw  new CommonServiceException(404,"用户编号为["+userId+"]的用户不存在");
        }
    }

    @Override
    public UserInfoVO updateUserInfo(UserInfoVO userInfoVO) throws CommonServiceException {
        NextUserT nextUserT = infoVOToUser(userInfoVO);
        if(userInfoVO != null && userInfoVO.getUuid() != null){
            int isSuccess = nextUserTMapper.updateById(nextUserT);
            if(isSuccess==1){
                UserInfoVO result = describeUserInfo(userInfoVO.getUuid()+"");
                return result;
            }else{
                throw  new CommonServiceException(500,"用户修改失败");
            }
        }else{
            throw  new CommonServiceException(404,"用户编号为["+userInfoVO.getUuid()+"]的用户不存在");
        }
    }

    @Override
    public String describeUserId(String username)throws CommonServiceException{
        if(ToolUtils.isEmpty(username)){
            throw new CommonServiceException(1,"username不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",username);
        List<NextUserT> result = nextUserTMapper.selectList(queryWrapper);
        if(result!=null && result.size()>0){
            NextUserT nextUserT = result.get(0);
            return nextUserT.getUuid()+"";
        }else{
            return "";
        }
    }
    /*
    -----------自定义方法----------
    * */
    private  UserInfoVO userToInfoVO(NextUserT nextUserT){
        UserInfoVO userInfoVO = new UserInfoVO();

        userInfoVO.setUsername(nextUserT.getUserName());
        userInfoVO.setNickname(nextUserT.getNickName());
        userInfoVO.setBeginTime(nextUserT.getBeginTime().toEpochSecond(ZoneOffset.of("+8")));
        userInfoVO.setUpdateTime(nextUserT.getUpdateTime().toEpochSecond(ZoneOffset.of("+8")));
        userInfoVO.setLifeState(nextUserT.getLifeState()+"");
        BeanUtils.copyProperties(nextUserT,userInfoVO);
        return userInfoVO;
    }
    private  NextUserT infoVOToUser(UserInfoVO userInfoVO){
        NextUserT nextUserT = new NextUserT();
        nextUserT.setUuid(userInfoVO.getUuid());
        nextUserT.setUserName(userInfoVO.getUsername());
        nextUserT.setNickName(userInfoVO.getNickname());

        nextUserT.setUpdateTime(LocalDateTime.now());
        //最好先用正则判断是否为数字类型再转换
        if(Optional.ofNullable(userInfoVO.getLifeState()).isPresent()){
            nextUserT.setLifeState(Integer.parseInt(userInfoVO.getLifeState()));
        }
        BeanUtils.copyProperties(userInfoVO,nextUserT);
        return nextUserT;
    }
}
