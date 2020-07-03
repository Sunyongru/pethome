package com.itsun.service.impl;


import com.itsun.dao.ChatInfoMapper;
import com.itsun.dao.ChatRoomMapper;
import com.itsun.entity.ChatInfo;
import com.itsun.entity.ChatRoom;
import com.itsun.service.ChatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService {

    private Logger logger= LoggerFactory.getLogger(ChatServiceImpl.class);

    @Autowired
    ChatRoomMapper chatRoomMapper;

    @Autowired
    ChatInfoMapper chatInfoMapper;

    @Override
    public int queryChatInfoByUserAccount(String loginUser, String receiver) {
        if (StringUtils.isBlank(loginUser)||StringUtils.isBlank(receiver)){
            return 0;
        }
        ChatInfo chatInfo=chatInfoMapper.selectByFATUserAccount(loginUser,receiver);
        if (chatInfo==null){
            return 0;
        }
        return chatInfo.getChatRoomId();
    }

    @Override
    public boolean creatChatRoomAndInfo(String userOne,String userTwo) {
        String chatName= UUID.randomUUID().toString().replaceAll("-","");
        ChatRoom chatRoom=new ChatRoom();
        chatRoom.setChatName(chatName);
        chatRoomMapper.insertSelective(chatRoom);
        int chatRoomId=chatRoom.getId();
        logger.info("刚插入的数据id:"+chatRoomId);
        if (chatRoomId<=0){
            return false;
        }else {
            Date date=new Date();
            ChatInfo chatInfoOne=new ChatInfo();
            chatInfoOne.setChatRoomId(chatRoomId);
            chatInfoOne.setFromUser(userOne);
            chatInfoOne.setToUser(userTwo);
            chatInfoOne.setCreatDate(date);
            int flagOne= chatInfoMapper.insertSelective(chatInfoOne);
            if (flagOne<=0){
                return false;
            }
            ChatInfo chatInfoTwo=new ChatInfo();
            chatInfoTwo.setChatRoomId(chatRoomId);
            chatInfoTwo.setFromUser(userTwo);
            chatInfoTwo.setToUser(userOne);
            chatInfoTwo.setCreatDate(date);
            int flagTwo= chatInfoMapper.insertSelective(chatInfoTwo);
            if (flagTwo<=0){
                return false;
            }
            return true;
        }
    }

    @Override
    public String queryChatName(String sender, String receiver) {
        ChatInfo chatInfo=chatInfoMapper.selectByFATUserAccount(sender,receiver);
        int chatRoomId=chatInfo.getChatRoomId();
        String chatName=chatRoomMapper.selectChatNameByPrimaryKey(chatRoomId);
        return chatName;
    }

    @Override
    public void setUnreadMsgNum(String sender, String receiver) {
        chatInfoMapper.setUnreadMsg(sender,receiver);
    }

    @Override
    public List<ChatInfo> queryChatInfo(String userAccount) {
        return chatInfoMapper.selectByToUser(userAccount);
    }

    @Override
    public void delUnreadMsg(String fromUser, String loginUser) {
        chatInfoMapper.delUnreadMsg(fromUser,loginUser);
    }
}
