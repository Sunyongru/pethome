package com.itsun.service;

import com.itsun.entity.FosterCare;
import com.itsun.vo.FosterVo;

import java.util.List;

public interface FosterCareService {
    int createFosterCareInfo(FosterCare fosterCare);

    List selectALLInfo();

    List selectALLMyInfoByUserAccount(String userAccount);

    FosterVo selectFosterInfo(Integer id);

    List selectFosterInfoByPro(String province);

    int deleteFosterInfoById(Integer id);

    List selectInfoByPetType(String petType);

    List selectInfoByPetTypeAndPro(String petType, String province);

    List selectFosterInfoByProAndCt(String province, String city);

    List selectInfoByPetTypeAndProAndCt(String petType, String province, String city);

    List selectFosterInfoByProAndCtAndAr(String province, String city, String area);

    List selectInfoByPetTypeAndProAndCtAndAr(String petType, String province, String city, String area);
}
