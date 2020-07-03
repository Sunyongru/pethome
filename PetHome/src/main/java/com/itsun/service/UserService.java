package com.itsun.service;

import com.itsun.entity.User;
import com.itsun.vo.LoginVo;
import com.itsun.vo.RegistVo;
import com.itsun.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    int registUser(RegistVo registVo);

    boolean checkUser(HttpServletResponse response, LoginVo loginVo);

    UserVo queryUserByAccount(String userAccount);

    int modifyUserByUserAccount(User user);
}
