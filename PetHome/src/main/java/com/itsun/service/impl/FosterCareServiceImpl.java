package com.itsun.service.impl;

import com.itsun.dao.FosterCareMapper;
import com.itsun.dao.UserMapper;
import com.itsun.entity.FosterCare;
import com.itsun.service.FosterCareService;
import com.itsun.vo.FosterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FosterCareServiceImpl implements FosterCareService {

    @Autowired
    FosterCareMapper fosterCareMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public int createFosterCareInfo(FosterCare fosterCare) {
        if (fosterCare==null){
            return 0;
        }
        return fosterCareMapper.insert(fosterCare);
    }

    @Override
    public List selectALLInfo() {
        return fosterCareMapper.selectAllInfo();
    }

    @Override
    public List selectALLMyInfoByUserAccount(String userAccount) {
        return fosterCareMapper.selectByUserAccount(userAccount);
    }

    @Override
    public FosterVo selectFosterInfo(Integer id) {
        FosterCare fosterCare=fosterCareMapper.selectByPrimaryKey(id);
        FosterVo fosterVo=new FosterVo();
        fosterVo.setId(id);
        fosterVo.setUserAccount(fosterCare.getUserAccount());
        fosterVo.setUserName(userMapper.selectByUserAccount(fosterCare.getUserAccount()).getUserName());
        fosterVo.setTitle(fosterCare.getTitle());
        fosterVo.setMoney(fosterCare.getMoney());
        fosterVo.setPetType(fosterCare.getPetType());
        fosterVo.setDateType(fosterCare.getDateType());
        fosterVo.setPetImg(fosterCare.getPetImg());
        fosterVo.setDescribetion(fosterCare.getDescribetion());
        fosterVo.setProvince(fosterCare.getProvince());
        fosterVo.setCity(fosterCare.getCity());
        fosterVo.setArea(fosterCare.getArea());
        fosterVo.setCreateDate(fosterCare.getCreateDate());
        return fosterVo;
    }

    @Override
    public List selectFosterInfoByPro(String province) {
        return fosterCareMapper.selectFosterInfoByPro(province);
    }

    @Override
    public int deleteFosterInfoById(Integer id) {
        return fosterCareMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List selectInfoByPetType(String petType) {
        return fosterCareMapper.selectInfoByPetType(petType);
    }

    @Override
    public List selectInfoByPetTypeAndPro(String petType, String province) {
        return fosterCareMapper.selectInfoByPetTypeAndPro(petType,province);
    }

    @Override
    public List selectFosterInfoByProAndCt(String province, String city) {
        return fosterCareMapper.selectFosterInfoByProAndCt(province,city);
    }

    @Override
    public List selectInfoByPetTypeAndProAndCt(String petType, String province, String city) {
        return fosterCareMapper.selectInfoByPetTypeAndProAndCt(petType,province,city);
    }

    @Override
    public List selectFosterInfoByProAndCtAndAr(String province, String city, String area) {
        return fosterCareMapper.selectFosterInfoByProAndCtAndAr(province,city,area);
    }

    @Override
    public List selectInfoByPetTypeAndProAndCtAndAr(String petType, String province, String city, String area) {
        return fosterCareMapper.selectInfoByPetTypeAndProAndCtAndAr(petType,province,city,area);
    }
}
