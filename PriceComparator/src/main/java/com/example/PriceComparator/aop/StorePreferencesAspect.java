package com.example.PriceComparator.aop;

import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class StorePreferencesAspect {

    private final CurrentUserService currentUserService;

    @Around("@annotation(com.example.PriceComparator.aop.FilterByStorePreferences)")
    public Object filterByStorePreferences(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        Set<Store> preferredStores = currentUserService.getPreferredStores();

        if (result instanceof Collection<?> collection) {
            return collection.stream()
                    .filter(item -> {
                        try {
                            Store store = extractStoreFromItem(item);
                            return store == null || preferredStores.contains(store);
                        } catch (Exception e) {
                            return true; // fail-safe: include item if error
                        }
                    })
                    .toList();
        }

        return result;
    }

    private Store extractStoreFromItem(Object item) {
        try {
            // Case: item has getStore() directly (e.g., StoreProduct)
            Method getStoreMethod = item.getClass().getMethod("getStore");
            return (Store) getStoreMethod.invoke(item);
        } catch (NoSuchMethodException e1) {
            try {
                // Case: Discount → getStoreProduct() → getStore()
                Method getStoreProduct = item.getClass().getMethod("getStoreProduct");
                Object storeProduct = getStoreProduct.invoke(item);
                if (storeProduct != null) {
                    Method getStore = storeProduct.getClass().getMethod("getStore");
                    return (Store) getStore.invoke(storeProduct);
                }
            } catch (Exception e2) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
