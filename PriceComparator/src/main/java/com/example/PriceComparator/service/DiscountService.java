package com.example.PriceComparator.service;

import com.example.PriceComparator.model.Discount;
import com.example.PriceComparator.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;

    public List<Discount> getBestDiscounts() {
        return discountRepository.findTopByOrderByDiscountPercentageDesc();
    }
}
