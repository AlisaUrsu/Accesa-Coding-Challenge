package com.example.PriceComparator.converter;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.dto.ProductHistoryDto;
import com.example.PriceComparator.dto.StoreProductHistoryDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StoreProductHistoryDtoConverter implements Converter<List<PriceHistory>, StoreProductHistoryDto> {
    @Override
    public StoreProductHistoryDto createFromEntity(List<PriceHistory> entity) {
        return null;
    }

    @Override
    public List<PriceHistory> createFromDto(StoreProductHistoryDto dto) {
        return null;
    }

    /**
     * This method groups a list of PriceHistory entities by store and then by product, for a better visualization
     * of the evolution of prices per store.
     * @param history
     * @return
     */
    public List<StoreProductHistoryDto> convertGroupedByStore(List<PriceHistory> history) {
        if (history.isEmpty()) {
            return List.of();
        }

        // This groups a list into a map where the key is the store name and the value if a list of price history records
        // for every product in that store
        Map<String, List<PriceHistory>> byStore = history.stream()
                .collect(Collectors.groupingBy(priceHistory -> priceHistory.getStore().getName()));

        // Looping over each entry in the store map
        return byStore.entrySet().stream()
                .map(storeEntry -> {
                    String storeName = storeEntry.getKey();
                    List<PriceHistory> storeHistories = storeEntry.getValue();

                    // This groups the map by the product id and the value represents each price the product has ever had
                    Map<String, List<PriceHistory>> byProduct = storeHistories.stream()
                            .collect(Collectors.groupingBy(priceHistory -> priceHistory.getProduct().getId()));

                    // Next, each product history from this list is converted into a DTO
                    List<ProductHistoryDto> productHistoryDtos = byProduct.values().stream()
                            .map(new ProductHistoryDtoConverter()::createFromEntity)
                            .toList();

                    // Return the DTO displaying the history of price for every product in a store
                    return new StoreProductHistoryDto(storeName, productHistoryDtos);
                })
                .toList();
    }
}
