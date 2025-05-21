package com.example.PriceComparator.service;

import com.example.PriceComparator.aop.FilterByStorePreferences;
import com.example.PriceComparator.model.Discount;
import com.example.PriceComparator.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;

    @FilterByStorePreferences
    public List<Discount> getBestDiscounts(LocalDate date) {
        return discountRepository.findTopByOrderByDiscountPercentageDesc(date);
    }

    @FilterByStorePreferences
    public List<Discount> getNewDiscounts(LocalDate date) {return discountRepository.findByFromDateAfter(date); }
}
