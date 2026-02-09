package com.dns.telegramBot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertsDto implements Serializable {
    private String status;
    private Object labels;
    private AnnotationsDto annotations;
    private String endsAt;
    private String generatorURL;
}
