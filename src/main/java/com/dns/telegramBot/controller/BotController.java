package com.dns.telegramBot.controller;

import com.dns.telegramBot.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/monitor")
public class BotController {

    @Autowired
    private SendMessageService sendMessageService;

    @PostMapping("/prometheus")
    public void postMapping(@RequestBody Map<String, Object> request) {
        sendMessageService.prometheusData(request);
    }

}
