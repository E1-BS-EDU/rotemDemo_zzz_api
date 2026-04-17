package com.rotemDemo.zzz.api.login.scheduler;

import com.rotemDemo.zzz.api.login.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {

    private final LoginMapper loginMapper;

    @Scheduled(cron = "0 0 3 * * *")
    public void deleteExpiredTokens() {
        int deleted = loginMapper.deleteExpiredTokens(new HashMap<>());
        log.info("만료된 Refresh Token 삭제 {}건", deleted);
    }
}
