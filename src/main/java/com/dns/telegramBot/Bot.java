package com.dns.telegramBot;

import com.dns.telegramBot.services.SendMessageService;
import com.dns.telegramBot.services.SendMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    private final SendMessageService sendMessageService;

    public Bot(SendMessageServiceImpl sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sm = null;
        try {
            sm = sendMessageService.send(update.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(sm);
        try {
            this.execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}