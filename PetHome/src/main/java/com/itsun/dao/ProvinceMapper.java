package com.itsun.dao;

import com.itsun.entity.Province;

import java.util.List;


public interface ProvinceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Province record);

    int insertSelective(Province record);

    Province selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Province record);

    int updateByPrimaryKey(Province record);

    List selectAllProvince();

    Province selectProvinceByPid(String pid);
}