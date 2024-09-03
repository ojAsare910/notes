package com.ojasare.notes.security.jwt;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklist {

    private final Set<String> blacklistedTokens = new HashSet<>();

    private final Map<String, Long> tokenExpiryMap = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 60000)
    public void cleanupExpiredTokens() {
        long currentTime = System.currentTimeMillis();
        tokenExpiryMap.entrySet().removeIf(entry -> {
            if (entry.getValue() <= currentTime) {
                blacklistedTokens.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }

    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
