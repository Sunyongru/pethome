package com.itsun.service.impl;

import com.itsun.dao.HelpCareMapper;
import com.itsun.dao.UserMapper;
import com.itsun.entity.HelpCare;
import com.itsun.service.HelpCareService;
import com.itsun.vo.HelpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpCareServiceImpl implements HelpCareService {

    @Autowired
    HelpCareMapper helpCareMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public int createHelpInfo(HelpCare helpCare) {
        return helpCareMapper.insertSelective(helpCare);
    }

    @Override
    public List selectAllHelpInfo() {
        return helpCareMapper.selectAllHelpInfo();
    }

    @Override
    public HelpVo selectFosterInfo(Integer id) {
        HelpCare helpCare=helpCareMapper.selectByPrimaryKey(id);
        HelpVo helpVo=new HelpVo();
        helpVo.setId(helpCare.getId());
        helpVo.setTitle(helpCare.getTitle());
        helpVo.setUserAccount(helpCare.getUserAccount());
        helpVo.setUserName(userMapper.selectByUserAccount(helpCare.getUserAccount()).getUserName());
        helpVo.setPetType(helpCare.getPetType());
        helpVo.setDateType(helpCare.getDateType());
        helpVo.setMoney(helpCare.getMoney());
        helpVo.setProvince(helpCare.getProvince());
        helpVo.setCity(helpCare.getCity());
        helpVo.setArea(helpCare.getArea());
        helpVo.setDescribetion(helpCare.getDescribetion());
        helpVo.setCreateDate(helpCare.getCreateDate());
        return helpVo;
    }

    @Override
    public List selectAllHelpInfoByUserAccount(String userAccount) {
        return helpCareMapper.selectAllHelpInfoByUserAccount(userAccount);
    }

    @Override
    public int deleteHelpInfoById(Integer id) {
        return helpCareMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List selectInfoByPet(String petType) {
        return helpCareMapper.selectInfoByPet(petType);
    }

    @Override
    public List selectInfoByPetTypeAndPro(String petType, String province) {
        return helpCareMapper.selectInfoByPetTypeAndPro(petType,province);
    }

    @Override
    public List selectInfoByPetTypeAndProAndCt(String petType, String province, String city) {
        return helpCareMapper.selectInfoByPetTypeAndProAndCt(petType,province,city);
    }

    @Override
    public List selectInfoByPetTypeAndProAndCtAndAr(String petType, String province, String city, String area) {
        return helpCareMapper.selectInfoByPetTypeAndProAndCtAndAr(petType,province,city,area);
    }
}
