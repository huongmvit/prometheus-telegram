package com.dns.telegramBot.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.Map;

public interface SendMessageService {

    SendMessage send(Message message) throws IOException, InterruptedException;

    void prometheusData(Map<String, Object> request);
}
