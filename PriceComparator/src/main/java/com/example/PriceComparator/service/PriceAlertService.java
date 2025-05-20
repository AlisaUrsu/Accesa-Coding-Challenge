package com.example.PriceComparator.service;

import com.example.PriceComparator.aop.FilterByStorePreferences;
import com.example.PriceComparator.model.*;
import com.example.PriceComparator.repository.DiscountRepository;
import com.example.PriceComparator.repository.PriceAlertRepository;
import com.example.PriceComparator.repository.StoreProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceAlertService {
    private final PriceAlertRepository priceAlertRepository;
    private final StoreProductRepository storeProductRepository;
    private final DiscountRepository discountRepository;

    @FilterByStorePreferences
    public void checkAlert() {
        List<PriceAlert> alerts = priceAlertRepository.findByTriggeredFalse();

        for (PriceAlert alert : alerts) {
            List<StoreProduct> storeProducts = storeProductRepository.findByProduct(alert.getProduct());

            for (StoreProduct sp : storeProducts) {
                BigDecimal basePrice = sp.getPrice();
                BigDecimal bestPrice = basePrice;

                // Check for an active discount
                Optional<Discount> activeDiscount = discountRepository
                        .findFirstByStoreProductAndFromDateLessThanEqualAndToDateGreaterThanEqualOrderByFromDateDesc(
                                sp,
                                LocalDate.now(),
                                LocalDate.now()
                        );

                if (activeDiscount.isPresent()) {
                    BigDecimal discountPercentage = activeDiscount.get().getPercentage();
                    BigDecimal discounted = basePrice.multiply(
                            BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
                    ).setScale(2, RoundingMode.HALF_UP);
                    bestPrice = discounted;
                }

                // Compare best price (discounted or base) with target
                if (bestPrice.compareTo(alert.getTargetPrice()) <= 0) {
                    alert.setTriggered(true);
                    priceAlertRepository.save(alert);
                    notifyUser(alert.getUser(), alert.getProduct(), sp.getStore().getName(), bestPrice);
                    break;
                }
            }
        }
    }

    private void notifyUser(User user, Product product, String store, BigDecimal price) {
        System.out.printf("ALERT: '%s' is now %.2f RON at %s for user %s%n",
                product.getName(), price, store, user.getUsername());
        // Replace with email logic if needed
    }

    public PriceAlert addPriceAlert(PriceAlert priceAlert) {
        return priceAlertRepository.save(priceAlert);
    }
}
