package com.itsun.common;

public class Constants {
    /**
     * 用户信息在session中存储的变量名
     */
    public static final String SESSION_USER = "userAccount";

    /**
     * 用户未读的WebSocket消息在Redis中存储的变量名的前缀
     */
    public static final String REDIS_UNREAD_MSG_PREFIX = "websocket:unread_msg:";

    /**
     * 用户是否有未读信息
     */
    public static final String USER_UNREAD="unreaduser:";
}
