package com.itsun.dao;

import com.itsun.entity.HelpCare;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HelpCareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HelpCare record);

    int insertSelective(HelpCare record);

    HelpCare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HelpCare record);

    int updateByPrimaryKey(HelpCare record);

    List selectAllHelpInfo();

    List selectAllHelpInfoByUserAccount(String userAccount);

    List selectInfoByPet(String petType);

    List selectInfoByPetTypeAndPro(@Param("petType") String petType, @Param("province") String province);

    List selectInfoByPetTypeAndProAndCt(@Param("petType") String petType,@Param("province") String province,@Param("city") String city);

    List selectInfoByPetTypeAndProAndCtAndAr(@Param("petType") String petType,@Param("province") String province,@Param("city") String city,@Param("area") String area);
}