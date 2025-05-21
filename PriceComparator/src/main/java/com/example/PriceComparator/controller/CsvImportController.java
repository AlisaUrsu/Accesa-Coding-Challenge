package com.example.PriceComparator.controller;

import com.example.PriceComparator.service.CsvImportService;
import com.example.PriceComparator.utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.endpoint.base-url}/csv-import")
@Tag(name = "Csv Import")
public class CsvImportController {
    private final CsvImportService csvImportService;

    @PostMapping
    public Result<?> populateDatabase() throws IOException {

        csvImportService.addUnits();
        csvImportService.importCsv("Lidl", LocalDate.of(2025, 5, 1), new FileInputStream("src/main/resources/products/lidl_2025-05-01.csv"));
        csvImportService.importCsv("Lidl", LocalDate.of(2025, 5, 8), new FileInputStream("src/main/resources/products/lidl_2025-05-08.csv"));
        csvImportService.importCsv("Profi", LocalDate.of(2025, 5, 1), new FileInputStream("src/main/resources/products/profi_2025-05-01.csv"));
        csvImportService.importCsv("Profi", LocalDate.of(2025, 5, 8), new FileInputStream("src/main/resources/products/profi_2025-05-08.csv"));
        csvImportService.importCsv("Kaufland", LocalDate.of(2025, 5, 1), new FileInputStream("src/main/resources/products/kaufland_2025-05-01.csv"));
        csvImportService.importCsv("Kaufland", LocalDate.of(2025, 5, 8), new FileInputStream("src/main/resources/products/kaufland_2025-05-08.csv"));

        csvImportService.importDiscountCsv("Lidl", LocalDate.of(2025, 5, 1), new FileInputStream("src/main/resources/discounts/lidl_discounts_2025-05-01.csv"));
        csvImportService.importDiscountCsv("Lidl", LocalDate.of(2025, 5, 8), new FileInputStream("src/main/resources/discounts/lidl_discounts_2025-05-08.csv"));
        csvImportService.importDiscountCsv("Lidl", LocalDate.of(2025, 5, 20), new FileInputStream("src/main/resources/discounts/lidl_discounts_2025-05-20.csv"));
        csvImportService.importDiscountCsv("Profi", LocalDate.of(2025, 5, 1), new FileInputStream("src/main/resources/discounts/profi_discounts_2025-05-01.csv"));
        csvImportService.importDiscountCsv("Profi", LocalDate.of(2025, 5, 8), new FileInputStream("src/main/resources/discounts/profi_discounts_2025-05-08.csv"));
        csvImportService.importDiscountCsv("Profi", LocalDate.of(2025, 5, 20), new FileInputStream("src/main/resources/discounts/profi_discounts_2025-05-20.csv"));
        csvImportService.importDiscountCsv("Kaufland", LocalDate.of(2025, 5, 1), new FileInputStream("src/main/resources/discounts/kaufland_discounts_2025-05-01.csv"));
        csvImportService.importDiscountCsv("Kaufland", LocalDate.of(2025, 5, 8), new FileInputStream("src/main/resources/discounts/kaufland_discounts_2025-05-08.csv"));
        csvImportService.importDiscountCsv("Kaufland", LocalDate.of(2025, 5, 20), new FileInputStream("src/main/resources/discounts/kaufland_discounts_2025-05-20.csv"));

        return new Result<>(true, HttpStatus.OK.value(), "Populated database successfully.", null);
    }
}
