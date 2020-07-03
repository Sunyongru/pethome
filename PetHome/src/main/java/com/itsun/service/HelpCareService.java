package com.itsun.service;

import com.itsun.entity.HelpCare;
import com.itsun.vo.HelpVo;

import java.util.List;

public interface HelpCareService {

    int createHelpInfo(HelpCare helpCare);

    List selectAllHelpInfo();

    HelpVo selectFosterInfo(Integer id);

    List selectAllHelpInfoByUserAccount(String userAccount);

    int deleteHelpInfoById(Integer id);

    List selectInfoByPet(String petType);

    List selectInfoByPetTypeAndPro(String petType, String province);

    List selectInfoByPetTypeAndProAndCt(String petType, String province, String city);

    List selectInfoByPetTypeAndProAndCtAndAr(String petType, String province, String city, String area);
}
