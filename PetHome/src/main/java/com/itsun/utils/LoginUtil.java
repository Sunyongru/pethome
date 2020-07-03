package com.itsun.utils;

import com.itsun.redis.RedisUtil;
import com.itsun.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Configuration
public class LoginUtil {

    @Autowired
    RedisUtil redisUtil;

    public void userIsLogin(HttpServletRequest request, Model model){
        Cookie[]cookies=request.getCookies();
        String str=null;
        if(cookies!=null){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    str=cookie.getValue();
                }
            }
            LoginVo loginVo=(LoginVo) redisUtil.get(str);
            if (loginVo!=null){
                HttpSession session=request.getSession();
                session.setAttribute("userAccount",loginVo.getUserAccount());
                model.addAttribute("userAccount",loginVo.getUserAccount());
                model.addAttribute("flag",true);
            }else {
                model.addAttribute("userAccount",null);
                model.addAttribute("flag",false);
            }
        }else {
            model.addAttribute("userAccount",null);
            model.addAttribute("flag",false);
        }
    }


    /**
     * 移除用户登录的token
     * @param request
     */
    public void remUserIsLogin(HttpServletRequest request){
        HttpSession session=request.getSession();
        session.removeAttribute("userAccount");
        Cookie []cookies=request.getCookies();
        String str=null;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                str=cookie.getValue();
            }
        }
        redisUtil.del(str);
    }
}
