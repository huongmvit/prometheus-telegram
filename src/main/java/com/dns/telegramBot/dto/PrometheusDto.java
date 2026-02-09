package com.dns.telegramBot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrometheusDto implements Serializable {
    private String version;
    private String groupKey;
    private Long truncatedAlerts;
    private String status;
    private Object groupLabels;
    private Object commonLabels;
    private Object commonAnnotations;
    private String externalURL;
    private List<AlertsDto> alerts;
}
