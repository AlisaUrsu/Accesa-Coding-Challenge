package com.example.PriceComparator.utils.dto;

import java.util.List;

public record UserDto (
        String username,
        String email,
        List<String> preferredStores
){
}
