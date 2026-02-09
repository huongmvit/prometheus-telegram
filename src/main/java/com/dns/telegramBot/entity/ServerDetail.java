package com.dns.telegramBot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "server_detail")
public class ServerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "host_ip")
    private String hostIp;

    @Column(name = "host_name")
    private String hostName;

    @Column(name = "use_cpu")
    private String useCpu;

    @Column(name = "use_ram")
    private String useRam;

    @Column(name = "use_disk")
    private String useDisk;

    @Column(name = "use_receive")
    private String useReceive;

    @Column(name = "use_transmit")
    private String useTransmit;

    @Column(name = "note")
    private String note;

    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private Timestamp updatedTime;

    @Column(name = "status")
    private int status;
}