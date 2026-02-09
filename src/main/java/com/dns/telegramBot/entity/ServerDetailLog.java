package com.dns.telegramBot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "server_detail_log")
public class ServerDetailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "server")
    private String server;

    @Column(name = "type_sms")
    private String typeSms;

    @Column(name = "value")
    private String value;

    @Column(name = "type_server")
    private String typeServer;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

}