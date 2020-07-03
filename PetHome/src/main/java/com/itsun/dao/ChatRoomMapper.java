package com.itsun.dao;

import com.itsun.entity.ChatRoom;

public interface ChatRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatRoom record);

    int insertSelective(ChatRoom record);

    ChatRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatRoom record);

    int updateByPrimaryKey(ChatRoom record);

    String selectChatNameByPrimaryKey(int chatRoomId);
}