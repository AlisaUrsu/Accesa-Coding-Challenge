package com.example.PriceComparator.utils;

import com.example.PriceComparator.service.PriceAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class AlertScheduler {
    private final PriceAlertService alertService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void runAlertCheck() {
        alertService.checkAlert();
    }
}
