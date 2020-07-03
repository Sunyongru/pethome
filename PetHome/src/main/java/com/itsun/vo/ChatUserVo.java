package com.itsun.vo;

public class ChatUserVo {

    private Integer chatRoomId;

    private String chatUser;

    private String chatUserName;

    private String chatUserImg;

    private Integer unReadMsg;

    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getChatUser() {
        return chatUser;
    }

    public void setChatUser(String chatUser) {
        this.chatUser = chatUser;
    }

    public String getChatUserName() {
        return chatUserName;
    }

    public void setChatUserName(String chatUserName) {
        this.chatUserName = chatUserName;
    }

    public String getChatUserImg() {
        return chatUserImg;
    }

    public void setChatUserImg(String chatUserImg) {
        this.chatUserImg = chatUserImg;
    }

    public Integer getUnReadMsg() {
        return unReadMsg;
    }

    public void setUnReadMsg(Integer unReadMsg) {
        this.unReadMsg = unReadMsg;
    }
}
