package com.dns.telegramBot.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class SendMessageLogic {

    @Value("${telegram.send_group1}")
    private String groupSend01;

    @Value("${telegram.bot.token}")
    private String tokenBot;

    @Autowired
    private RestTemplate restTemplate;

    public void sendSmsPoint(String message) {
        this.sendMsg(message);
    }

    public void sendMsg(String sm) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("chat_id", groupSend01);
        map.add("text", sm);
        map.add("parse_mode", "HTML");
        String urlRequest = "https://api.telegram.org/bot".concat(tokenBot.concat("/sendMessage"));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        restTemplate.postForEntity(urlRequest, request, String.class);
    }
}
