package com.example.PriceComparator.service;

import com.example.PriceComparator.model.*;
import com.example.PriceComparator.repository.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CsvImportService {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final StoreProductRepository storeProductRepository;
    private final UnitRepository unitRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final DiscountRepository discountRepository;
    private final DiscountHistoryRepository discountHistoryRepository;

    public void importCsv(String storeName, LocalDate localDate, InputStream csvStream) throws IOException {
        // Get or create a new store if not present in the database
        Store store = storeRepository.findByName(storeName).orElseGet(() -> {
            Store newStore = new Store();
            newStore.setName(storeName);
            return storeRepository.save(newStore);
        });

        try (CSVReader reader = new CSVReader(new InputStreamReader(csvStream))) {
            reader.readNext(); // Skip header row

            String[] row;
            while ((row = reader.readNext()) != null) {
                // Parse CSV columns
                String productId = row[0];
                String productName = row[1];
                String categoryName = row[2];
                String brandName = row[3];
                BigDecimal quantity = new BigDecimal(row[4]);
                String unitName = row[5];
                BigDecimal price = new BigDecimal(row[6]);
                String currency = row[7];

                // Get or create new Category if not existing
                Category category = categoryRepository.findByName(categoryName)
                        .orElseGet(() -> categoryRepository.save(new Category(null, categoryName)));

                // Get or create new Brand if not existing
                Brand brand = brandRepository.findByName(brandName)
                        .orElseGet(() -> brandRepository.save(new Brand(null, brandName)));

                // Get the Unit from database; Unit table stores units like "kg", "g", "buc" and other popular units
                Unit unit = unitRepository.findByName(unitName)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown unit: " + unitName));

                // Get or create Product if not in database, based on the .csv file
                Product product = productRepository.findById(productId).orElseGet(() -> {
                    Product newProduct = Product.builder()
                            .id(productId)
                            .name(productName)
                            .category(category)
                            .brand(brand)
                            .packageQuantity(quantity)
                            .packageUnit(unit)
                            .build();
                    return productRepository.save(newProduct);
                });

                // Compute price per standard unit (e.g. g -> kg or ml -> l)
                // price / (quantity * unit conversion factor)
                BigDecimal conversionFactor = unit.getConversionFactor(); // e.g., 0.001 for "g" to "kg"
                BigDecimal quantityInStandardUnit = quantity.multiply(conversionFactor);

                BigDecimal pricePerStandardUnit = quantityInStandardUnit.compareTo(BigDecimal.ZERO) > 0
                        ? price.divide(quantityInStandardUnit, 4, RoundingMode.HALF_UP)
                        : null;

                // PriceHistory table stores every product and its price, no matter what the date is
                PriceHistory priceHistory = PriceHistory.builder()
                        .store(store)
                        .product(product)
                        .date(localDate)
                        .price(price)
                        .pricePerUnit(pricePerStandardUnit)
                        .currency(currency)
                        .build();
                priceHistoryRepository.save(priceHistory);

                // StoreProduct table store only the newest prices
                Optional<StoreProduct> existingStoreProduct = storeProductRepository.findByStoreAndProduct(store, product);

                if (existingStoreProduct.isEmpty()) {
                    // Create a new StoreProduct entry if not in database
                    StoreProduct newStoreProduct = StoreProduct.builder()
                            .store(store)
                            .product(product)
                            .date(localDate)
                            .price(price)
                            .currency(currency)
                            .pricePerUnit(pricePerStandardUnit)
                            .build();

                    storeProductRepository.save(newStoreProduct);
                } else {
                    // If such a product exist, the date and price will be updated accordingly
                    StoreProduct storeProduct = existingStoreProduct.get();
                    if (localDate.isAfter(storeProduct.getDate())) {
                        storeProduct.setPrice(price);
                        storeProduct.setDate(localDate);
                        storeProduct.setPricePerUnit(pricePerStandardUnit);
                        storeProductRepository.save(storeProduct);
                    }
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void importDiscountCsv(String storeName, LocalDate importDate, InputStream csvStream) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");

        // Store has to exist firstly
        Store store = storeRepository.findByName(storeName)
                .orElseThrow(() -> new IllegalArgumentException("Store not found: " + storeName));

        try (CSVReader reader = new CSVReader(new InputStreamReader(csvStream))) {
            reader.readNext(); // skip header

            String[] row;
            while ((row = reader.readNext()) != null) {
                String productId = row[0];
                LocalDate fromDate = LocalDate.parse(row[6], dateTimeFormatter);
                LocalDate toDate = LocalDate.parse(row[7], dateTimeFormatter);
                BigDecimal percentage = new BigDecimal(row[8]);

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

                StoreProduct storeProduct = storeProductRepository.findByStoreAndProduct(store, product)
                        .orElseThrow(() -> new IllegalStateException("StoreProduct not found for product " + productId + " in store " + storeName));


                DiscountHistory discountHistory = DiscountHistory.builder()
                        .storeProduct(storeProduct)
                        .fromDate(fromDate)
                        .toDate(toDate)
                        .percentage(percentage)
                        .build();

                discountHistoryRepository.save(discountHistory);

                Optional<Discount> existingDiscount = discountRepository.findByStoreProduct(storeProduct);

                if (existingDiscount.isEmpty()) {
                    Discount newDiscount = Discount.builder()
                            .storeProduct(storeProduct)
                            .date(importDate)
                            .fromDate(fromDate)
                            .toDate(toDate)
                            .percentage(percentage)
                            .build();

                    discountRepository.save(newDiscount);
                } else {
                    Discount discount = existingDiscount.get();
                    if (importDate.isAfter(discount.getDate())) {
                       discount.setDate(importDate);
                       discount.setFromDate(fromDate);
                       discount.setToDate(toDate);
                       discount.setPercentage(percentage);
                        discountRepository.save(discount);
                    }
                }

            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

}
