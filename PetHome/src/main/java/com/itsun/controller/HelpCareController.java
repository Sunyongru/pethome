package com.itsun.controller;

import com.itsun.entity.Area;
import com.itsun.entity.City;
import com.itsun.entity.HelpCare;
import com.itsun.entity.Province;
import com.itsun.result.CodeMsg;
import com.itsun.result.Result;
import com.itsun.service.BaseService;
import com.itsun.service.HelpCareService;
import com.itsun.utils.BaseUtil;
import com.itsun.utils.LoginUtil;
import com.itsun.vo.FosterVo;
import com.itsun.vo.HelpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/help")
@Controller
public class HelpCareController {

    @Autowired
    LoginUtil loginUtil;

    @Autowired
    BaseUtil baseUtil;

    @Autowired
    BaseService baseService;

    @Autowired
    HelpCareService helpCareService;

    @RequestMapping("/addHelp")
    @ResponseBody
    public Result<Integer> addHelp(@RequestParam("userAccount")String userAccount,
                                     @RequestParam("title")String title,
                                     @RequestParam("petType")String petType,
                                     @RequestParam("dateType")String dateType,
                                     @RequestParam("money")String money,
                                     @RequestParam("describe")String describe,
                                     @RequestParam("province")String province,
                                     @RequestParam("city")String city,
                                     @RequestParam("area")String area){
        HelpCare helpCare=new HelpCare();
        if (title.length()<=0){
            helpCare.setTitle("未填写标题");
        }
        helpCare.setTitle(title);
        helpCare.setUserAccount(userAccount);
        helpCare.setPetType(petType);
        helpCare.setDateType(dateType);
        if (money.length()<=0){
            helpCare.setMoney("未填写报酬");
        }
        helpCare.setMoney(money);
        helpCare.setDescribetion(describe);
        helpCare.setProvince(province);
        helpCare.setCity(city);
        helpCare.setArea(area);
        int flag= helpCareService.createHelpInfo(helpCare);
        if (flag<=0){
            return Result.error(CodeMsg.CREATE_INFO_FAILE);
        }
        return Result.success(flag);
    }
    @RequestMapping("/helpdetails/{helpid}")
    public String fosterDitle(@PathVariable("helpid")Integer id,
                              HttpServletRequest request, Model model){
        loginUtil.userIsLogin(request,model);
        HelpVo helpVo= helpCareService.selectFosterInfo(id);
        model.addAttribute("helpVo",helpVo);
        return "helpDetails";
    }

    @RequestMapping("/delete/{helpId}")
    @ResponseBody
    public Result<Integer> deleteInfo(@PathVariable("helpId")Integer id){
        int flag=helpCareService.deleteHelpInfoById(id);
        if (flag<=0){
            return Result.error(CodeMsg.DELETE_FAILE);
        }
        return Result.success(flag);
    }

    /**
     * 用于转向帮养界面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/helpCare/{type}")
    public String helpCare(@PathVariable("type")String type, HttpServletRequest request,Model model){
        loginUtil.userIsLogin(request,model);
        setHtmlDisplay(model,type);
        return "helpCare";
    }

    private void setHtmlDisplay(Model model,String type){
        List list=null;
        String str[]=type.split("_");
        String petType=baseUtil.setPetType(model,str[0]);
        if (str.length==1){
            list=setPetType(model,petType);
        }
        if (str.length==2){
            String pid=str[1].substring(1);
            Province province= baseService.getProvinceByPid(pid);
            list= provinceSet(model,petType,province.getProvince(),pid);
        }
        if (str.length==3){
            String pid=str[1].substring(1);
            Province province=baseService.getProvinceByPid(pid);
            if (str[2].equals("c0")){
                list=provinceSet(model,petType,province.getProvince(),pid);
            }else {
                String cid=str[2].substring(1);
                City city=baseService.getCityByCid(cid);
                list=citySet(model,petType,province.getProvince(),pid,city.getCity(),cid);
            }
        }
        if (str.length==4){
            String pid=str[1].substring(1);
            Province province=baseService.getProvinceByPid(pid);
            String cid=str[2].substring(1);
            City city=baseService.getCityByCid(cid);
            if (str[3].equals("a0")){
                list=citySet(model,petType,province.getProvince(),pid,city.getCity(),cid);
            }else {
                String aid=str[3].substring(1);
                Area area=baseService.getAreaByAid(aid);
                list=areaSet(model,petType,province.getProvince(),pid,city.getCity(),cid,area.getArea(),aid);
            }
        }
        model.addAttribute("helpInfoList",list);
    }

    private List setPetType(Model model,String petType){
        List list=helpCareService.selectInfoByPet(petType);
        baseUtil.setNoPrCtAr(model);
        return list;
    }

    private List provinceSet(Model model,String petType,String province,String pid){
        List list=null;
        list=helpCareService.selectInfoByPetTypeAndPro(petType,province);
        baseUtil.setPro(model, province, pid);
        return list;
    }

    private List citySet(Model model,String petType,String province,String pid,String city,String cid){
        List list=null;
        list=helpCareService.selectInfoByPetTypeAndProAndCt(petType,province,city);
        baseUtil.setProAndCity(model, province, pid, city, cid);
        return list;
    }

    private List areaSet(Model model,String petType,String province,String pid,String city,String cid,String area,String aid){
        List list=null;
        list=helpCareService.selectInfoByPetTypeAndProAndCtAndAr(petType,province,city,area);
        baseUtil.setProAndCityAndArea(model, province, pid, city, cid, area, aid);
        return list;
    }

}
