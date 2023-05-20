package com.socket.websocket.controller;


import com.socket.websocket.domain.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class GreetingController {
    private final SimpMessagingTemplate template;
    @MessageMapping("/enter")
    @SendTo("/topic/greetings")
    public HelloMessage enter(StompHeaderAccessor headerAccessor) throws Exception {
        return new HelloMessage(HtmlUtils.htmlEscape(headerAccessor.getSessionAttributes().get("name") + "님께서 입장하셨습니다!"));
    }


    @MessageMapping("/chat")
    @SendTo("/topic/greetings")
    public HelloMessage chat(HelloMessage message, StompHeaderAccessor headerAccessor) throws Exception {
        System.out.println("chat headerAccessor = " + headerAccessor);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date now = new Date();

        String currentTime = format.format(now);

        System.out.println(currentTime);
        return new HelloMessage(HtmlUtils.htmlEscape(headerAccessor.getSessionAttributes().get("name") + " : " + message.getContent() + "[" + currentTime + "]"));
    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());
        HelloMessage me = new HelloMessage(HtmlUtils.htmlEscape(  header.getSessionAttributes().get("name")+"님께서 퇴장하셨습니다!"));
        template.convertAndSend("/topic/greetings", me);

    }
}