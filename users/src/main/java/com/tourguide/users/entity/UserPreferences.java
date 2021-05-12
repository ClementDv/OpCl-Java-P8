package com.tourguide.users.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPreferences {
    private final int attractionProximity = Integer.MAX_VALUE;
    private final CurrencyUnit currency = Monetary.getCurrency("USD");
    private final Money lowerPricePoint = Money.of(0, currency);
    private final Money highPricePoint = Money.of(Integer.MAX_VALUE, currency);

    @Builder.Default
    private int tripDuration = 1;
    private final int ticketQuantity = 1;
    private final int numberOfAdults = 1;
    private final int numberOfChildren = 0;
}
