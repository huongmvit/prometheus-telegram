package com.dns.telegramBot.repo;

import com.dns.telegramBot.entity.ServerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerDetailRepo extends JpaRepository<ServerDetail, Long> {
    Optional<ServerDetail> findTopByHostIp(String hostIp);
}
