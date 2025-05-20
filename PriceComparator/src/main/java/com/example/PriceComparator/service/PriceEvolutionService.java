package com.example.PriceComparator.service;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.repository.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceEvolutionService {
    private final PriceHistoryRepository priceHistoryRepository;

    public List<PriceHistory> getPriceTrendsById(String id) {
        return priceHistoryRepository.findByProductIdOrderByDateAsc(id);
    }

    public List<PriceHistory> getPriceTrendsFiltered(String store, String brand, String category) {
        return priceHistoryRepository.findByFilters(store, brand, category);
    }

}
