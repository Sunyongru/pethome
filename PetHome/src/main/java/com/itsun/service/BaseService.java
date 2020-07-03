package com.itsun.service;


import com.itsun.entity.Area;
import com.itsun.entity.City;
import com.itsun.entity.Province;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.List;

public interface BaseService {

    BufferedImage createVerifyCode(String time, HttpServletRequest request);

    boolean checkVerifyCode(int verifyCode, HttpServletRequest request);

    List getProvince();

    List getCity(String pid);

    List getArea(String cid);

    Province getProvinceByPid(String str);

    City getCityByCid(String cid);

    Area getAreaByAid(String aid);
}
