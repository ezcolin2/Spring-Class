package com.example.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @PostMapping("/fcm")
    public String fcm(@RequestBody RequestDto requestDto) throws FirebaseMessagingException {
        String registrationToken = "기기 토큰";
        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getContent())
                .setImage("이미지 url")
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(registrationToken)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);
        return response;
    }
}
