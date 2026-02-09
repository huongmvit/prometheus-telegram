package com.dns.telegramBot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendSmsDto implements Serializable {
    @JsonProperty("chat_id")
    private @NonNull String chatId;
    @JsonProperty("text")
    private @NonNull String text;
    @JsonProperty("parse_mode")
    private String parseMode;
}
