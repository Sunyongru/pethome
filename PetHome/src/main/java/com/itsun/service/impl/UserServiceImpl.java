package com.itsun.service.impl;

import com.itsun.dao.UserMapper;
import com.itsun.entity.User;
import com.itsun.redis.RedisUtil;
import com.itsun.service.UserService;
import com.itsun.utils.MD5Util;
import com.itsun.utils.UUIDUtil;
import com.itsun.vo.LoginVo;
import com.itsun.vo.RegistVo;
import com.itsun.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int registUser(RegistVo registVo) {
        User user= userMapper.selectByUserAccount(registVo.getUserAccount());
        if (user!=null){
            return -1;
        }
        user=new User();
        user.setUserAccount(registVo.getUserAccount());
        user.setUserName(registVo.getUserAccount());
        user.setPhoneNum(registVo.getUserAccount());
        String dbPassword= MD5Util.inputPassToFormPass(registVo.getPassword());
        user.setPassword(dbPassword);
        int flag= userMapper.insertSelective(user);
        return flag;
    }

    @Override
    public boolean checkUser(HttpServletResponse response, LoginVo loginVo) {
        User user=userMapper.selectByUserAccount(loginVo.getUserAccount());
        if (user==null){
            return false;
        }
        String dbpassword=user.getPassword();
        if (!dbpassword.equals(loginVo.getPassword())){
            return false;
        }
        String token= UUIDUtil.uuid();
        addCookie(response,token,loginVo);
        return true;
    }

    @Override
    public UserVo queryUserByAccount(String userAccount) {
        User user= userMapper.selectByUserAccount(userAccount);
        UserVo userVo=new UserVo();
        userVo.setUserAccount(userAccount);
        userVo.setUserName(user.getUserName());
        userVo.setUserImg(user.getUserImg());
        userVo.setProvince(user.getProvince());
        userVo.setCity(user.getCity());
        userVo.setArea(user.getArea());
        userVo.setAddress(user.getAddress());
        userVo.setPhoneNum(user.getPhoneNum());
        return userVo;
    }

    @Override
    public int modifyUserByUserAccount(User user) {
        return userMapper.updateByUserAccount(user);
    }

    private void addCookie(HttpServletResponse response, String token,LoginVo loginVo) {
        redisUtil.set(token,loginVo,3600*24);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(3600*24);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
