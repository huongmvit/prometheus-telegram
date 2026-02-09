package com.dns.telegramBot.messenger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatTelegramDto implements Serializable {
    private Long id;
    private String type;
    private String title;
    private String firstName;
    private String lastName;
    private String userName;
}
