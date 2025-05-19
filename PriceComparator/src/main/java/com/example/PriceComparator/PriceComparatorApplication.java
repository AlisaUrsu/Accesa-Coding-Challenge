package com.example.PriceComparator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class PriceComparatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(PriceComparatorApplication.class, args);
	}
}
