package com.itsun.dao;

import com.itsun.entity.FosterCare;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FosterCareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FosterCare record);

    int insertSelective(FosterCare record);

    FosterCare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FosterCare record);

    int updateByPrimaryKey(FosterCare record);

    List selectAllInfo();

    List selectByUserAccount(String userAccount);

    List selectFosterInfoByPro(String province);

    List selectInfoByPetType(String petType);

    List selectInfoByPetTypeAndPro(@Param("petType") String petType,@Param("province") String province);

    List selectFosterInfoByProAndCt(@Param("province") String province,@Param("city") String city);

    List selectInfoByPetTypeAndProAndCt(@Param("petType") String petType,@Param("province") String province,@Param("city") String city);

    List selectFosterInfoByProAndCtAndAr(@Param("province") String province,@Param("city") String city,@Param("area") String area);

    List selectInfoByPetTypeAndProAndCtAndAr(@Param("petType") String petType,@Param("province") String province,@Param("city") String city,@Param("area") String area);
}