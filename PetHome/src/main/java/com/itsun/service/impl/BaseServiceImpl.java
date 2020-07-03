package com.itsun.service.impl;

import com.itsun.dao.AreaMapper;
import com.itsun.dao.CityMapper;
import com.itsun.dao.ProvinceMapper;
import com.itsun.entity.Area;
import com.itsun.entity.City;
import com.itsun.entity.Province;
import com.itsun.redis.RedisUtil;
import com.itsun.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ProvinceMapper provinceMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    AreaMapper areaMapper;

    @Override
    public BufferedImage createVerifyCode(String verifyValue, HttpServletRequest request) {
        int width = 80;
        int height = 30;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        int rnd = calc(verifyCode);
        HttpSession session=request.getSession();
        redisUtil.set(session.toString()+":"+verifyValue,rnd,30);
        //输出图片
        return image;
    }

    public boolean checkVerifyCode(int verifyCode, HttpServletRequest request) {
        HttpSession session=request.getSession();
        int value=(int)redisUtil.get(session.toString()+":verifyValue");
        if (value!=verifyCode){
            return false;
        }
        return true;
    }

    @Override
    public List getProvince() {
        List list=provinceMapper.selectAllProvince();
        return list;
    }

    @Override
    public List getCity(String pid) {
        List list=cityMapper.selectCityByPid(pid);
        return list;
    }

    @Override
    public List getArea(String cid) {
        List list=areaMapper.selectAreaByCid(cid);
        return list;
    }

    @Override
    public Province getProvinceByPid(String pid) {
        return provinceMapper.selectProvinceByPid(pid);
    }

    @Override
    public City getCityByCid(String cid) {
        return cityMapper.selectCityByCid(cid);
    }

    @Override
    public Area getAreaByAid(String aid) {
        return areaMapper.selectAreaByAid(aid);
    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }
}
