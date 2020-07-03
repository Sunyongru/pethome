package com.itsun.controller;

import com.itsun.redis.RedisUtil;
import com.itsun.result.Result;
import com.itsun.service.FosterCareService;
import com.itsun.service.HelpCareService;
import com.itsun.service.UserService;
import com.itsun.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("pethome")
public class FirstPageController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginUtil loginUtil;

    @Autowired
    FosterCareService fosterCareService;

    @Autowired
    UserService userService;

    @Autowired
    HelpCareService helpCareService;


    /**
     * 用于转向网站首页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model){
        loginUtil.userIsLogin(request,model);
        return "index";
    }


    /**
     * 注销操作
     * @param request
     * @return
     */
    @RequestMapping("/exit")
    @ResponseBody
    public Result<String> exit(HttpServletRequest request){
        loginUtil.remUserIsLogin(request);
        return Result.success("success");
    }

}
