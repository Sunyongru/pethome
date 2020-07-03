package com.itsun.dao;

import com.itsun.entity.ChatInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatInfo record);

    int insertSelective(ChatInfo record);

    ChatInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatInfo record);

    int updateByPrimaryKey(ChatInfo record);

    ChatInfo selectByFATUserAccount(@Param("sender") String sender,@Param("receiver") String receiver);

    void setUnreadMsg(@Param("sender") String sender,@Param("receiver") String receiver);

    List<ChatInfo> selectByToUser(String receiver);

    void delUnreadMsg(@Param("sender") String sender,@Param("receiver") String receiver);
}