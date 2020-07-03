package com.itsun.controller;

import com.itsun.common.Constants;
import com.itsun.entity.City;
import com.itsun.entity.Province;
import com.itsun.redis.RedisUtil;
import com.itsun.result.CodeMsg;
import com.itsun.result.Result;
import com.itsun.service.BaseService;
import com.itsun.service.UserService;
import com.itsun.utils.LoginUtil;
import com.itsun.vo.LoginVo;
import com.itsun.vo.RegistVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping("/operation")
public class BaseOperationController {

    @Autowired
    UserService userService;

    @Autowired
    BaseService baseService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginUtil loginUtil;

    /**
     * 用于转向注册界面
     * @return
     */
    @RequestMapping("to_regist")
    public String to_register(){
        return "regist";
    }

    @RequestMapping("/regist")
    @ResponseBody
    public Result<Integer> regist(@Valid RegistVo registVo,HttpServletRequest request){
        if (registVo==null){
            return Result.error(CodeMsg.REGIST_FAIL);
        }
        int verifyCode=registVo.getVerifyCode();
        boolean isRight=baseService.checkVerifyCode(verifyCode,request);
        if (!isRight){
            return Result.error(CodeMsg.VERIFYCODE_ERROR);
        }
        int flag= userService.registUser(registVo);
        if (flag==-1){
            return Result.error(CodeMsg.USER_ALREADY_EXIST);
        }
        if (flag<=0){
            return Result.error(CodeMsg.REGIST_FAIL);
        }
        return Result.success(flag);
    }

    /**
     * 用于转向登录界面
     * @return
     */
    @RequestMapping("/to_login")
    public String to_login(){
        return "login";
    }

    /**
     * 登录操作
     * @param response
     * @param loginVo
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result<Boolean> login(HttpServletResponse response, @Valid LoginVo loginVo){
        if (loginVo==null){
            return Result.error(CodeMsg.LOGIN_ERROR);
        }
        boolean isLogin= userService.checkUser(response,loginVo);
        if (!isLogin){
            return Result.error(CodeMsg.LOGIN_ERROR);
        }
        return Result.success(isLogin);
    }

    /**
     * 用于生成验证码
     * @param response
     * @param verifyValue
     * @return
     */
    @RequestMapping("/verifyCode")
    @ResponseBody
    public Result<String> verify(HttpServletRequest request, HttpServletResponse response, @RequestParam("verifyValue")String verifyValue){
        BufferedImage image=baseService.createVerifyCode(verifyValue,request);
        try{
            OutputStream out=response.getOutputStream();
            ImageIO.write(image,"JPEG",out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return Result.error(CodeMsg.REGIST_FAIL);
        }
    }

    @RequestMapping("/getProvince")
    @ResponseBody
    public Result<List> getProvince(Model model){
        List list=baseService.getProvince();
        model.addAttribute("data",list);
        return Result.success(list);
    }

    @RequestMapping("/getCity/{pid}")
    @ResponseBody
    public Result<List> getCity(@PathVariable("pid")String pid){
        List list=baseService.getCity(pid);
        return Result.success(list);
    }

    @RequestMapping("/getArea/{cid}")
    @ResponseBody
    public Result<List> getArea(@PathVariable("cid")String cid){
        List list=baseService.getArea(cid);
        return Result.success(list);
    }

    @RequestMapping("/getUnRead/{userAccount}")
    @ResponseBody
    public int getUnRead(@PathVariable("userAccount")String userAccount){
        Object o=redisUtil.get(Constants.USER_UNREAD+userAccount);
        if (o==null){
            return 0;
        }else {
           int x=(int)redisUtil.get(Constants.USER_UNREAD+userAccount);
           return x;
        }
    }

    @RequestMapping("/setLastURL")
    @ResponseBody
    public void setLastURL(@RequestParam("lastURL")String lastURL,@RequestParam("user")String userAccount){
        if (StringUtils.isNotBlank(lastURL)){
            redisUtil.set(userAccount+"lastURL",lastURL);
        }
    }

    @RequestMapping("/getLastURL")
    @ResponseBody
    public String getLastURL(@RequestParam("user")String userAccount){
        String lastURL=(String) redisUtil.get(userAccount+"lastURL");
        System.out.println(lastURL);
        redisUtil.del(userAccount+"lastURL");
        return lastURL;
    }
}
