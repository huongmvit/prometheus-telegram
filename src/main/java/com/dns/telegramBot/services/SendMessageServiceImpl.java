package com.dns.telegramBot.services;

import com.dns.telegramBot.dto.AlertsDto;
import com.dns.telegramBot.dto.AnnotationsDto;
import com.dns.telegramBot.dto.PrometheusDto;
import com.dns.telegramBot.entity.ServerDetail;
import com.dns.telegramBot.entity.ServerDetailLog;
import com.dns.telegramBot.logic.SendMessageLogic;
import com.dns.telegramBot.messenger.ChatTelegramDto;
import com.dns.telegramBot.messenger.FromTelegramDto;
import com.dns.telegramBot.messenger.MessengerDto;
import com.dns.telegramBot.repo.ServerDetailLogRepo;
import com.dns.telegramBot.repo.ServerDetailRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    private SendMessageLogic sendMessageLogic;

    @Autowired
    private ServerDetailLogRepo serverDetailLogRepo;

    @Autowired
    private ServerDetailRepo serverDetailRepo;

    @Override
    public SendMessage send(Message message) {
        String data = message.getText();
        SendMessage sendMessage = SendMessage.builder()
                .text("Gửi thông báo sang nhóm")
                .parseMode("HTML")
                .chatId(String.valueOf(message.getChatId()))
                .build();
        String highDiskUsageStart = "";
        String networkReceiveHigh = "";
        String highRAMUsage = "";
        String networkTransmitHigh = "";
        String highCPUUsage = "";

        if (data.indexOf("HighDiskUsageStart") > 0) {
            highDiskUsageStart = data.substring(data.indexOf("HighDiskUsageStart"), data.indexOf("HighDiskUsageEnd"));
            setTelegram(highDiskUsageStart, "HighDiskUsageStart");
        }
        if (data.indexOf("NetworkReceiveHighStart") > 0) {
            networkReceiveHigh = data.substring(data.indexOf("NetworkReceiveHighStart"), data.indexOf("NetworkReceiveHighEnd"));
            setTelegram(networkReceiveHigh, "NetworkReceiveHighStart");
        }
        if (data.indexOf("HighRAMUsageStart") > 0) {
            highRAMUsage = data.substring(data.indexOf("HighRAMUsageStart"), data.indexOf("HighRAMUsageEnd"));
            setTelegram(highRAMUsage, "HighRAMUsageStart");
        }
        if (data.indexOf("NetworkTransmitHighStart") > 0) {
            networkTransmitHigh = data.substring(data.indexOf("NetworkTransmitHighStart"), data.indexOf("NetworkTransmitHighEnd"));
            setTelegram(networkTransmitHigh, "NetworkTransmitHighStart");
        }
        if (data.indexOf("HighCPUUsageStart") > 0) {
            highCPUUsage = data.substring(data.indexOf("HighCPUUsageStart"), data.indexOf("HighCPUUsageEnd"));
            setTelegram(highCPUUsage, "HighCPUUsageStart");
        }

        return sendMessage;
    }

    @Override
    public void prometheusData(Map<String, Object> request) {
        Gson gson = new Gson();
        String requestJson = gson.toJson(request);
        PrometheusDto prometheusDto = gson.fromJson(requestJson, PrometheusDto.class);
        if (prometheusDto.getAlerts().size() > 0) {
            for (int i = 0; i < prometheusDto.getAlerts().size(); i++) {
                AlertsDto alertsDto = prometheusDto.getAlerts().get(i);
                AnnotationsDto annotationsDto = alertsDto.getAnnotations();
                sendTelegram(annotationsDto.getDescription());
            }
        }
    }

    public void sendTelegram(String data) {
        String highDiskUsageStart = "";
        String networkReceiveHigh = "";
        String highRAMUsage = "";
        String networkTransmitHigh = "";
        String highCPUUsage = "";

        if (data.contains("HighDiskUsageStart")) {
            highDiskUsageStart = data.substring(data.indexOf("HighDiskUsageStart"), data.indexOf("HighDiskUsageEnd"));
            setTelegram(highDiskUsageStart, "HighDiskUsageStart");
        }
        if (data.contains("NetworkReceiveHighStart")) {
            networkReceiveHigh = data.substring(data.indexOf("NetworkReceiveHighStart"), data.indexOf("NetworkReceiveHighEnd"));
            setTelegram(networkReceiveHigh, "NetworkReceiveHighStart");
        }
        if (data.contains("HighRAMUsageStart")) {
            highRAMUsage = data.substring(data.indexOf("HighRAMUsageStart"), data.indexOf("HighRAMUsageEnd"));
            setTelegram(highRAMUsage, "HighRAMUsageStart");
        }
        if (data.contains("NetworkTransmitHighStart")) {
            networkTransmitHigh = data.substring(data.indexOf("NetworkTransmitHighStart"), data.indexOf("NetworkTransmitHighEnd"));
            setTelegram(networkTransmitHigh, "NetworkTransmitHighStart");
        }
        if (data.contains("HighCPUUsageStart")) {
            highCPUUsage = data.substring(data.indexOf("HighCPUUsageStart"), data.indexOf("HighCPUUsageEnd"));
            setTelegram(highCPUUsage, "HighCPUUsageStart");
        }

    }

    private String[] setTelegram(String highDisk, String type) {
        System.out.println(highDisk);
        String highDiskUsageStart = highDisk.replace(type, "");
        LocalDateTime dateTime = LocalDateTime.now();
        String[] hightDisk = highDiskUsageStart.split("-");
        saveToDB(hightDisk, type);
        String dataSend =
                "<i>Thời gian thông báo: </i><b> " + dateTime + " </b>\n" +
                        "<i>Loại thông báo: </i><b> " + type + " </b>\n" +
                        "<i>Ip server: </i><b>" + hightDisk[0].trim() + " </b>\n" +
                        "<i>Giá trị: </i><b>" + hightDisk[1].trim() + "</b>\n" +
                        "<i>Thông số: </i><b>" + hightDisk[2].trim() + "</b>";

        sendMessageLogic.sendMsg(dataSend);
        return hightDisk;
    }

    private void saveToDB(String[] hightDisk, String type) {
        String hostIp = hightDisk[0];
       Optional<ServerDetail> ServerDetailOpt = serverDetailRepo.findTopByHostIp(hostIp.trim());
       if(ServerDetailOpt.isPresent()) {
           if (type.equals("HighDiskUsageStart")) {
               ServerDetail serverDetail = ServerDetailOpt.get();
               serverDetail.setUseDisk(hightDisk[1]);
               insertDb(serverDetail, "HighCPUUsageStart");
           }
           if (type.equals("NetworkReceiveHighStart")) {
               ServerDetail serverDetail = ServerDetailOpt.get();
               serverDetail.setUseReceive(hightDisk[1]);
               insertDb(serverDetail, "NetworkReceiveHighStart");
           }
           if (type.equals("HighRAMUsageStart")) {
               ServerDetail serverDetail = ServerDetailOpt.get();
               serverDetail.setUseRam(hightDisk[1]);
               insertDb(serverDetail, "HighRAMUsageStart");
           }
           if (type.equals("NetworkTransmitHighStart")) {
               ServerDetail serverDetail = ServerDetailOpt.get();
               serverDetail.setUseTransmit(hightDisk[1]);
               insertDb(serverDetail, "NetworkTransmitHighStart");
           }
           if (type.equals("HighCPUUsageStart")) {
               ServerDetail serverDetail = ServerDetailOpt.get();
               serverDetail.setUseCpu(hightDisk[1]);
               insertDb(serverDetail, "HighCPUUsageStart");
           }

       }
        ServerDetailLog serverDetailLog = new ServerDetailLog();
        serverDetailLog.setServer(hightDisk[0]);
        serverDetailLog.setTypeServer(hightDisk[2]);
        serverDetailLog.setTypeSms(type);
        serverDetailLog.setValue(hightDisk[1]);
        serverDetailLogRepo.save(serverDetailLog);
    }


    private void insertDb(ServerDetail serverDetail, String type) {
        ServerDetail serverDetailRes = serverDetailRepo.save(serverDetail);
        if(serverDetailRes == null) {
            System.out.println("Loi insert ".concat(type));
        }
    }
    private MessengerDto setMessenger(Message message) {
        Gson gson = new Gson();
        String fromJson = gson.toJson(message);
        FromTelegramDto fromDto = this.setFrom(message);
        ChatTelegramDto chatDto = this.setChat(message);
        MessengerDto msgDto = new MessengerDto();
        msgDto.setMessageId(Long.parseLong(message.getMessageId() + ""));
        msgDto.setFrom(fromDto);
        msgDto.setChat(chatDto);
        msgDto.setDate(message.getDate());
        msgDto.setText(message.getText());
        return msgDto;
    }


    private FromTelegramDto setFrom(Message message) {
        Gson gson = new Gson();
        String fromJson = gson.toJson(message.getFrom());
        return gson.fromJson(fromJson, FromTelegramDto.class);
    }

    private ChatTelegramDto setChat(Message message) {
        Gson gson = new Gson();
        String fromJson = gson.toJson(message.getChat());
        return gson.fromJson(fromJson, ChatTelegramDto.class);
    }
}