package com.example.PriceComparator.service;

import com.example.PriceComparator.model.*;
import com.example.PriceComparator.repository.*;
import com.example.PriceComparator.utils.dto.BasketItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ShoppingOptimizerService {
    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListItemRepository shoppingListItemRepository;
    private final StoreProductRepository storeProductRepository;
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public List<ShoppingList> generateOptimizedLists(User user, List<BasketItemDto> basket) {
        Map<Store, List<ShoppingListItem>> storeMap = new HashMap<>();
        for (BasketItemDto basketItem: basket) {
            Product product = productRepository.findById(basketItem.productId()).orElseThrow();
            List<StoreProduct> options = storeProductRepository.findByProduct(product);

            StoreProduct best = null;
            BigDecimal bestPrice = null;

            for (StoreProduct storeProduct: options) {
                BigDecimal basePrice = storeProduct.getPrice();
                Optional<Discount> discount =
                        discountRepository.findFirstByStoreProductAndFromDateLessThanEqualAndToDateGreaterThanEqualOrderByFromDateDesc(
                                storeProduct, LocalDate.now(), LocalDate.now());
                BigDecimal finalPrice = basePrice;

                if (discount.isPresent()) {
                    BigDecimal percentage = discount.get().getPercentage();
                    finalPrice = basePrice.multiply(
                            BigDecimal.ONE.subtract(percentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
                    );
                }
                if (best == null || finalPrice.compareTo(bestPrice) < 0) {
                    best = storeProduct;
                    bestPrice = finalPrice;
                }
            }

            BigDecimal finalPrice = bestPrice.multiply(BigDecimal.valueOf(basketItem.quantity()));


            Store store = best.getStore();
            ShoppingListItem item = ShoppingListItem.builder()
                    .product(product)
                    .price(finalPrice)
                    .quantity(basketItem.quantity())
                    .build();

            storeMap.computeIfAbsent(store, s -> new ArrayList<>()).add(item);
        }
        List<ShoppingList> lists = new ArrayList<>();
        for (Map.Entry<Store, List<ShoppingListItem>> entry : storeMap.entrySet()) {
            List<ShoppingListItem> items = entry.getValue();
            BigDecimal totalPrice = BigDecimal.ZERO;

            for (ShoppingListItem item: items) {
                totalPrice = totalPrice.add(item.getPrice());
            }
            ShoppingList list = ShoppingList.builder()
                    .user(user)
                    .totalPrice(totalPrice)
                    .store(entry.getKey())
                    .name("Shopping List - " + entry.getKey().getName() + " | " + LocalDate.now())
                    .build();

            shoppingListRepository.save(list);

            for (ShoppingListItem item : entry.getValue()) {
                item.setShoppingList(list);
                shoppingListItemRepository.save(item);
            }

            list.setItems(entry.getValue());
            lists.add(list);
        }

        return lists;
    }
}
