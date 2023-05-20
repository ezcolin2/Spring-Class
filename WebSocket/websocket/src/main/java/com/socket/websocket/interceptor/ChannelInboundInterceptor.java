package com.socket.websocket.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Component
public class ChannelInboundInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(header.getCommand())) {
            //connect라면 name값을 꺼내서 sessionAttributes에 넣기.
            Map<String, Object> attributes = header.getSessionAttributes();
            attributes.put("name", header.getFirstNativeHeader("name"));
            header.setSessionAttributes(attributes);
        }

        return message;
    }
}
