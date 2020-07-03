package com.itsun.controller;

import com.itsun.entity.ChatInfo;
import com.itsun.entity.User;
import com.itsun.result.CodeMsg;
import com.itsun.result.Result;
import com.itsun.service.ChatService;
import com.itsun.service.FosterCareService;
import com.itsun.service.HelpCareService;
import com.itsun.service.UserService;
import com.itsun.utils.LoginUtil;
import com.itsun.vo.ChatUserVo;
import com.itsun.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    HelpCareService helpCareService;

    @Autowired
    LoginUtil loginUtil;

    @Autowired
    FosterCareService fosterCareService;

    @Autowired
    ChatService chatService;

    /**
     * 用于转向个人信息首页
     * @param userAccount
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/userInfo/{userAccount}")
    public String queryUserByAccount(@PathVariable("userAccount") String userAccount,
                                     HttpServletRequest request, Model model){
        loginUtil.userIsLogin(request,model);
        if ("".equals(userAccount)){
            return null;
        }
        UserVo userVo= userService.queryUserByAccount(userAccount);
        model.addAttribute("userVo",userVo);
        return "myInfo";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Result<Integer> modifyUserInfo(@RequestParam("userAccount")String userAccount,
                                          @RequestParam("userName")String userName,
                                          @RequestParam("userImg")String userImg,
                                          @RequestParam("province")String province,
                                          @RequestParam("city")String city,
                                          @RequestParam("area")String area,
                                          @RequestParam("address")String address,
                                          @RequestParam("phoneNum")String phoneNum){
        User user=new User();
        user.setUserAccount(userAccount);
        user.setUserName(userName);
        user.setUserImg(userImg);
        user.setProvince(province);
        user.setCity(city);
        user.setArea(area);
        user.setAddress(address);
        user.setPhoneNum(phoneNum);
        int x= userService.modifyUserByUserAccount(user);
        if(x<0){
            return Result.error(CodeMsg.UPDATE_ERROR);
        }
        return Result.success(x);
    }

    @RequestMapping("/myFoster/{userAccount}")
    public String selectMyFosterInfo(@PathVariable("userAccount")String userAccount,
                                     HttpServletRequest request,Model model){
        loginUtil.userIsLogin(request,model);
        List list= fosterCareService.selectALLMyInfoByUserAccount(userAccount);
        model.addAttribute("myFosterList",list);
        return "myfosterInfo";
    }
    @RequestMapping("/myHelp/{userAccount}")
    public String selectMyHelpInfo(@PathVariable("userAccount")String userAccount,
                                     HttpServletRequest request,Model model){
        loginUtil.userIsLogin(request,model);
        List list= helpCareService.selectAllHelpInfoByUserAccount(userAccount);
        model.addAttribute("myHelpList",list);
        return "myhelpInfo";
    }

    @RequestMapping("/myChat/{userAccount}")
    public String selectMyChat(@PathVariable("userAccount")String userAccount,
                               HttpServletRequest request,Model model){
        loginUtil.userIsLogin(request,model);
        List<ChatUserVo> chatUserVoList=new ArrayList<>();
        List<ChatInfo> list=chatService.queryChatInfo(userAccount);
        for (ChatInfo chatInfo:list){
            ChatUserVo chatUserVo=new ChatUserVo();
            chatUserVo.setChatRoomId(chatInfo.getChatRoomId());
            chatUserVo.setChatUser(chatInfo.getFromUser());
            UserVo userVo=userService.queryUserByAccount(chatInfo.getFromUser());
            chatUserVo.setChatUserName(userVo.getUserName());
            chatUserVo.setChatUserImg(userVo.getUserImg());
            chatUserVo.setUnReadMsg(chatInfo.getUnReadMsg());
            chatUserVoList.add(chatUserVo);
        }
        model.addAttribute("chatUserVolist",chatUserVoList);
        return "myChatInfo";
    }


    @RequestMapping("/to_chat/{userAccount}")
    public String to_chat(@PathVariable("userAccount")String userAccount, HttpServletRequest request, Model model){
        loginUtil.userIsLogin(request,model);
        HttpSession session=request.getSession();
        String fromUserAccount=(String) session.getAttribute("userAccount");
        UserVo fromUserVo=userService.queryUserByAccount(fromUserAccount);
        UserVo toUserVo= userService.queryUserByAccount(userAccount);
        model.addAttribute("fromUser",fromUserVo);
        model.addAttribute("toUser",toUserVo);
        return "chat";
    }
    @RequestMapping("/my_chat/{userAccount}")
    public String my_chat(@PathVariable("userAccount")String userAccount, HttpServletRequest request, Model model){
        loginUtil.userIsLogin(request,model);
        HttpSession session=request.getSession();
        String fromUserAccount=(String) session.getAttribute("userAccount");
        UserVo fromUserVo=userService.queryUserByAccount(fromUserAccount);
        UserVo toUserVo= userService.queryUserByAccount(userAccount);
        model.addAttribute("fromUser",fromUserVo);
        model.addAttribute("toUser",toUserVo);
        return "my_chat";
    }

}
