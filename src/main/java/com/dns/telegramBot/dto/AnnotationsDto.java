package com.dns.telegramBot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationsDto implements Serializable {
    private String description;
    private String summary;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private String generatorURL;
    private String fingerprint;

}
