package com.itsun.dao;

import com.itsun.entity.City;

import java.util.List;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    List selectCityByPid(String pid);

    City selectCityByCid(String cid);
}