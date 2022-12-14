package com.egc.bankservice.config.security.loginattempt;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptServiceBean implements LoginAttemptService {

    private static final int MAX_ATTEMPT = 3;

    private static final int EXPIRE_IN_MINUTES = 30;
    private final LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptServiceBean() {
        attemptsCache = CacheBuilder.newBuilder()
                                    .expireAfterWrite(EXPIRE_IN_MINUTES, TimeUnit.MINUTES)
                                    .build(new CacheLoader<>() {
                                        public Integer load(String key) {
                                            return 0;
                                        }
                                    });
    }

    @Override
    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    @Override
    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException ignored) {
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    @Override
    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
