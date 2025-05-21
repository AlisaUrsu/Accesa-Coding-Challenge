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

/**
 * Aspect that intercepts methods annotated with @FilterByStorePreferences and filters their result collections
 * to only include products from the current user's preferred stores
 */
@Aspect
@Component
@RequiredArgsConstructor
public class StorePreferencesAspect {

    private final CurrentUserService currentUserService;

    @Around("@annotation(com.example.PriceComparator.aop.FilterByStorePreferences)")
    public Object filterByStorePreferences(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        if (!currentUserService.isAuthenticated()) {
            return result;
        }

        // Get the current user's preferred stores
        Set<Store> preferredStores = currentUserService.getPreferredStores();

        // If the result is a Collection, apply store filtering
        if (result instanceof Collection<?> collection) {
            return collection.stream()
                    .filter(item -> {
                        try {
                            Store store = extractStoreFromItem(item);
                            if (store == null) {
                                return true; // allow item if store is unknown (fail-safe)
                            }

                            if (preferredStores.contains(store)) {
                                return true; // allow item if store is in user's preferences
                            }

                            return false; // otherwise, exclude the item
                        } catch (Exception e) {
                            return true;
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
                // Case: Discount -> getStoreProduct() -> getStore()
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
