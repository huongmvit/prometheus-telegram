package com.dns.telegramBot.messenger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessengerDto implements Serializable {
    private Long messageId;
    private FromTelegramDto from;
    private Integer date;
    private ChatTelegramDto chat;
    private String text;
}
