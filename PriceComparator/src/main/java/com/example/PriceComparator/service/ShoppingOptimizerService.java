package com.example.PriceComparator.service;

import com.example.PriceComparator.model.*;
import com.example.PriceComparator.repository.*;
import com.example.PriceComparator.dto.BasketItemDto;
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

    /**
     * Generates optimized shopping lists by selecting the lowest price for each product
     * (considering current discounts) from the user's preferred stores.
     *
     * @param user The current user
     * @param basket The list of products with chosen quantities
     * @param unavailableProducts A list to store names of products that couldn't be found
     * @return A list of optimized shopping lists, one per store
     */
    public List<ShoppingList> generateOptimizedLists(User user, List<BasketItemDto> basket, List<String> unavailableProducts) {
        Map<Store, List<ShoppingListItem>> storeMap = new HashMap<>();

        for (BasketItemDto basketItem: basket) {
            // Get product from database
            Product product = productRepository.findById(basketItem.productId()).orElseThrow();
            List<StoreProduct> options = storeProductRepository.findByProduct(product);

            // If no options exist (usually when a product can be found only in a store the user does not want
            // to buy from), the product names will be stored in a list to be displayed in the response
            if (options.isEmpty()) {
                unavailableProducts.add(product.getName());
                continue;
            }

            // Find the best/lowest price for an option
            StoreProduct best = null;
            BigDecimal bestPrice = null;

            for (StoreProduct storeProduct: options) {
                BigDecimal basePrice = storeProduct.getPrice();

                // Check if there is an active discount for this product in this store
                Optional<Discount> discount =
                        discountRepository.findActiveDiscountForProduct(storeProduct, LocalDate.now());

                // Apply discount if available
                BigDecimal finalPrice = basePrice;
                if (discount.isPresent()) {
                    BigDecimal percentage = discount.get().getPercentage();
                    // Logic: finalPrice = basePrice * (1 - (percentage / 100))
                    finalPrice = basePrice.multiply(
                            BigDecimal.ONE.subtract(percentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
                    );
                }

                // Choose the best price
                if (best == null || finalPrice.compareTo(bestPrice) < 0) {
                    best = storeProduct;
                    bestPrice = finalPrice;
                }
            }

            // Calculate final price based on selected quantity
            BigDecimal finalPrice = bestPrice.multiply(BigDecimal.valueOf(basketItem.quantity()));

            // Next, an item is created for this shopping list
            Store store = best.getStore();
            ShoppingListItem item = ShoppingListItem.builder()
                    .product(product)
                    .price(finalPrice)
                    .quantity(basketItem.quantity())
                    .build();

            // Add item to the list for its store
            storeMap.computeIfAbsent(store, s -> new ArrayList<>()).add(item);
        }

        // Generate one ShoppingList per store
        List<ShoppingList> lists = new ArrayList<>();
        for (Map.Entry<Store, List<ShoppingListItem>> entry : storeMap.entrySet()) {
            List<ShoppingListItem> items = entry.getValue();

            // Calculate total price for this store's shopping list
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (ShoppingListItem item: items) {
                totalPrice = totalPrice.add(item.getPrice());
            }

            // Creating and saving the new list
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

            // This is for returning lists that will be displayed later
            list.setItems(entry.getValue());
            lists.add(list);
        }

        return lists;
    }
}
