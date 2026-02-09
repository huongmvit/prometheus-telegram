package com.dns.telegramBot.repo;

import com.dns.telegramBot.entity.ServerDetailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerDetailLogRepo extends JpaRepository<ServerDetailLog, Long> {
}
