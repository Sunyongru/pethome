package com.itsun.websocket;

import com.itsun.common.Constants;
import com.itsun.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

@Component
public class MyHandshakeHandler extends DefaultHandshakeHandler {

    private Logger logger= LoggerFactory.getLogger(MyHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpSession session = SpringContextUtils.getSession();
        String loginUser = (String) session.getAttribute(Constants.SESSION_USER);

        if(loginUser != null){
            logger.debug(MessageFormat.format("WebSocket连接开始创建Principal，用户：{0}",loginUser));
            return new MyPrincipal(loginUser);
        }else{
            logger.error("未登录系统，禁止连接WebSocket");
            return null;
        }
    }
}
