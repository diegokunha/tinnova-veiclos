package com.tinnova.veiculos.integration.impl;

import com.tinnova.veiculos.integration.DollarExchangeService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
public class DollarExchangeServiceImpl implements DollarExchangeService {

    private static final String CACHE_KEY = "DOLAR_ATUAL";

    private final RedisTemplate<String, Object> redisTemplate;

    public DollarExchangeServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public BigDecimal getDollarRate() {

        Object cached = redisTemplate.opsForValue().get(CACHE_KEY);
        if (cached != null) {
            return (BigDecimal) cached;
        }

        BigDecimal dolar = BigDecimal.valueOf(5.00);

        redisTemplate.opsForValue().set(
                CACHE_KEY,
                dolar,
                Duration.ofMinutes(10)
        );

        return dolar;
    }
}
