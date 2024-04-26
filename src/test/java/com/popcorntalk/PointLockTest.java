package com.popcorntalk;

import com.popcorntalk.domain.exchange.service.ExchangeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@SpringBootTest
public class PointLockTest {

    @Autowired
    ExchangeServiceImpl exchangeService;

    @Test
    void test() throws InterruptedException {
        exchangeService.createExchange(1L, 1L);
    }
}
