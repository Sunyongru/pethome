package com.itsun.controller;


import com.itsun.dao.ProvinceMapper;
import com.itsun.entity.Area;
import com.itsun.entity.City;
import com.itsun.entity.FosterCare;
import com.itsun.entity.Province;
import com.itsun.result.CodeMsg;
import com.itsun.result.Result;
import com.itsun.service.BaseService;
import com.itsun.service.FosterCareService;
import com.itsun.utils.BaseUtil;
import com.itsun.utils.LoginUtil;
import com.itsun.vo.FosterVo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/foster")
@Controller
public class FosterCareController {

    @Autowired
    LoginUtil loginUtil;

    @Autowired
    BaseUtil baseUtil;

    @Autowired
    FosterCareService fosterCareService;

    @Autowired
    BaseService baseService;

    @RequestMapping("/addfoster")
    @ResponseBody
    public Result<Integer> addFoster(@RequestParam("userAccount")String userAccount,
                            @RequestParam("title")String title,
                            @RequestParam("petType")String petType,
                            @RequestParam("dateType")String dateType,
                            @RequestParam("money")String money,
                            @RequestParam("fosterImg")String fosterImg,
                            @RequestParam("describe")String describe,
                            @RequestParam("province")String province,
                            @RequestParam("city")String city,
                            @RequestParam("area")String area){
        FosterCare fosterCare=new FosterCare();
        if (title.length()<=0){
            fosterCare.setTitle("未填写标题");
        }
        fosterCare.setTitle(title);
        fosterCare.setUserAccount(userAccount);
        fosterCare.setPetType(petType);
        fosterCare.setDateType(dateType);
        if (money.length()<=0){
            fosterCare.setMoney("未填写报酬");
        }
        fosterCare.setMoney(money);
        fosterCare.setPetImg(fosterImg);
        fosterCare.setDescribetion(describe);
        fosterCare.setProvince(province);
        fosterCare.setCity(city);
        fosterCare.setArea(area);
        int flag= fosterCareService.createFosterCareInfo(fosterCare);
        if (flag<=0){
            return Result.error(CodeMsg.CREATE_INFO_FAILE);
        }
        return Result.success(flag);
    }

    @RequestMapping("/fosterdetails/{fosterid}")
    public String fosterDitle(@PathVariable("fosterid")Integer id,
                              HttpServletRequest request,Model model){
        loginUtil.userIsLogin(request,model);
        FosterVo fosterVo= fosterCareService.selectFosterInfo(id);
        model.addAttribute("fosterVo",fosterVo);
        return "fosterDetails";
    }

    @RequestMapping("/delete/{fosterId}")
    @ResponseBody
    public Result<Integer> deleteInfo(@PathVariable("fosterId")Integer id){
        int flag=fosterCareService.deleteFosterInfoById(id);
        if (flag<=0){
            return Result.error(CodeMsg.DELETE_FAILE);
        }
        return Result.success(flag);
    }

    /**
     * 用于转向寄养界面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/fosterCare/{type}")
    public String fosterCare(@PathVariable("type")String type,HttpServletRequest request,Model model){
        loginUtil.userIsLogin(request,model);
        setHtmlDisplay(model,type);
        return "fosterCare";
    }

    /**
     * 这是用于在寄养页面进行展示的内容
     * @param model
     * @param type
     */
    private void setHtmlDisplay(Model model,String type){
        //定义存储数据的集合
        List list=null;
        //分割请求路径
        String[] str = type.split("_");
        String petType=baseUtil.setPetType(model,str[0]);
        //当只选择宠物类型时做的方法
        if (str.length==1){
           list=petType(model,petType);
        }
        //当选择省和宠物类型做的方法
        if (str.length==2){
            if (str[1].equals("s0")){
                list=petType(model,petType);
            }else {
                String pid=str[1].substring(1);
                Province province= baseService.getProvinceByPid(pid);
                list= provinceSet(model,petType,province.getProvince(),pid);
            }
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
        model.addAttribute("fosterInfoList",list);
    }
    private List petType(Model model,String petType){
        List list=null;
        if ("不限".equals(petType)){
            list= fosterCareService.selectALLInfo();
        }else {
            list=fosterCareService.selectInfoByPetType(petType);
        }
        baseUtil.setNoPrCtAr(model);
        return list;
    }

    private List provinceSet(Model model,String petType,String province,String pid){
        List list=null;
        if ("不限".equals(petType)){
            list= fosterCareService.selectFosterInfoByPro(province);
        }else {
            list=fosterCareService.selectInfoByPetTypeAndPro(petType,province);
        }
        baseUtil.setPro(model, province, pid);
        return list;
    }

    private List citySet(Model model,String petType,String province,String pid,String city,String cid){
        List list=null;
        if ("不限".equals(petType)){
            list=fosterCareService.selectFosterInfoByProAndCt(province,city);
        }else {
            list=fosterCareService.selectInfoByPetTypeAndProAndCt(petType,province,city);
        }
        baseUtil.setProAndCity(model, province, pid, city, cid);
        return list;
    }

    private List areaSet(Model model,String petType,String province,String pid,String city,String cid,String area,String aid){
        List list=null;
        if ("不限".equals(petType)){
            list=fosterCareService.selectFosterInfoByProAndCtAndAr(province,city,area);
        }else {
            list=fosterCareService.selectInfoByPetTypeAndProAndCtAndAr(petType,province,city,area);
        }
        baseUtil.setProAndCityAndArea(model, province, pid, city, cid, area, aid);
        return list;
    }
}
