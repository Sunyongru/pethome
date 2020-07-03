package com.itsun.service;

import com.itsun.entity.ChatInfo;

import java.util.List;

public interface ChatService {
    int queryChatInfoByUserAccount(String loginUser,String receiver);

    boolean creatChatRoomAndInfo(String userOne,String userTwo);

    String queryChatName(String sender, String receiver);

    void setUnreadMsgNum(String sender, String receiver);

    List<ChatInfo> queryChatInfo(String userAccount);

    void delUnreadMsg(String fromUser, String loginUser);
}
