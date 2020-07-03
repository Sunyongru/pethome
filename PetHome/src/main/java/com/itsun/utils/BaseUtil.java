package com.itsun.utils;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

@Configuration
public class BaseUtil {

    public String setPetType(Model model,String str){
        String petType=null;
        //设置宠物类型选项
        switch (str){
            case "my":model.addAttribute("petType","不限");petType="不限";break;
            case "p0": model.addAttribute("petType","猫类");petType="猫类";break;
            case "p1": model.addAttribute("petType","犬类");petType="犬类";break;
            case "p2": model.addAttribute("petType","水生类");petType="水生类";break;
            case "p3": model.addAttribute("petType","其他类");petType="其他类";break;
        }
        //设置宠物类型路径
        model.addAttribute("path",str);
        return petType;
    }

    public void setNoPrCtAr(Model model){
        model.addAttribute("qp","");
        model.addAttribute("province","不限");
        model.addAttribute("p_path","s0");
        model.addAttribute("city","不限");
        model.addAttribute("c_path","c0");
        model.addAttribute("area","不限");
        model.addAttribute("a_path","a0");
    }

    public void setPro(Model model,String province,String pid){
        model.addAttribute("qp","_s"+pid);
        model.addAttribute("province",province);
        model.addAttribute("p_path","_s"+pid);
        model.addAttribute("city","不限");
        model.addAttribute("c_path","c0");
        model.addAttribute("area","不限");
        model.addAttribute("a_path","a0");
    }

    public void setProAndCity(Model model,String province,String pid,String city,String cid){
        model.addAttribute("qp","_s"+pid+"_c"+cid);
        model.addAttribute("province",province);
        model.addAttribute("p_path","_s"+pid);
        model.addAttribute("city",city);
        model.addAttribute("c_path","_c"+cid);
        model.addAttribute("area","不限");
        model.addAttribute("a_path","a0");
    }
    public void setProAndCityAndArea(Model model,String province,String pid,String city,String cid,String area,String aid){
        model.addAttribute("qp","_s"+pid+"_c"+cid+"_a"+aid);
        model.addAttribute("province",province);
        model.addAttribute("p_path","_s"+pid);
        model.addAttribute("city",city);
        model.addAttribute("c_path","_c"+cid);
        model.addAttribute("area",area);
        model.addAttribute("a_path","_a"+aid);
    }
}
