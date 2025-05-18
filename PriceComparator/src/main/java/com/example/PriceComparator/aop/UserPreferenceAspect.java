package com.example.PriceComparator.aop;

import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.service.CurrentUserService;
import com.example.PriceComparator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class UserPreferenceAspect {

    private final CurrentUserService currentUserService;

    @Around("@annotation(FilterByUserPreferences)")
    public Object filterByUserPreferences(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        // Retrieve user preferred stores
        Set<Store> preferredStores = currentUserService.getPreferredStores();

        // Inject filtering where possible (e.g., via method arguments)
        for (Object arg : args) {
            if (arg instanceof StoreFilterAware) {
                ((StoreFilterAware) arg).setAllowedStores(preferredStores);
            }
        }

        return joinPoint.proceed(args);
    }
}
