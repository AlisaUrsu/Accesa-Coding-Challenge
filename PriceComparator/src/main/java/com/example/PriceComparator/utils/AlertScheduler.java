package com.example.PriceComparator.utils;

import com.example.PriceComparator.service.PriceAlertService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class AlertScheduler {
    private final PriceAlertService alertService;

    public AlertScheduler(PriceAlertService alertService) {
        this.alertService = alertService;
    }

    @Scheduled(fixedRate = 5000)
    public void runAlertCheck() {
        alertService.checkAlert();
    }
}
