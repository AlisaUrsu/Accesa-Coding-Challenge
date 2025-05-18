package com.example.PriceComparator;

import com.example.PriceComparator.model.Unit;
import com.example.PriceComparator.repository.UnitRepository;
import com.example.PriceComparator.service.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class PriceComparatorApplication  {
	public static void main(String[] args) {
		SpringApplication.run(PriceComparatorApplication.class, args);
	}
}