package com.itsun.entity;

import java.util.Date;

public class ChatInfo {
    private Integer id;

    private Integer chatRoomId;

    private String fromUser;

    private String toUser;

    private Integer unReadMsg;

    private Date creatDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public Integer getUnReadMsg() {
        return unReadMsg;
    }

    public void setUnReadMsg(Integer unReadMsg) {
        this.unReadMsg = unReadMsg;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }
}