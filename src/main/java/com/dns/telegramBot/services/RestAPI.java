package com.dns.telegramBot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

@Component
public class RestAPI {
    @Autowired
    RestTemplate restTemplate;

    public String restApiPost(String tokenParam, String url, List<String> arHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(tokenParam,headers);
        if (arHeader != null) {
            for (int i=0; i<arHeader.size(); i++) {
                String keyHeader = arHeader.get(i).toString();
                i++;
                String valHeader = arHeader.get(i).toString();
                headers.set(keyHeader, valHeader);
            }
        }
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    }
    public String resApiToken(String url,String body) {
        System.out.println("====TRINGEE===");
        System.out.println(url);
        System.out.println(body);
        HttpHeaders	headers = new HttpHeaders();
        //Set Content Type
        headers.setContentType(MediaType.APPLICATION_JSON);
        //requestEntity : Body+Header
        HttpEntity<String> request = new HttpEntity<String> (body,headers);
        // 2. make HTTP call and store Response (URL,ResponseType)
        //	ResponseEntity<String> response =  restTemplate.postForEntity(url, request, String.class);
        return restTemplate.exchange(url, HttpMethod.POST,request, String.class).getBody();
    }
}
