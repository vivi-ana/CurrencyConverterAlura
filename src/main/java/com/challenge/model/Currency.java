package com.challenge.model;

import java.util.Map;

/**
 * Represents currency data containing the base code and conversion rates to other currencies.
 */
public record Currency (String base_code, Map<String, Double> conversion_rates) {
    public Double getConversionRate(String currency) {
        if (conversion_rates.containsKey(currency)) {
            return conversion_rates.get(currency);
        } else {
            throw new IllegalArgumentException("The currency " + currency + " is not available in the results");
        }
    }

    public Double calculateConvertedAmount(Double amount, String targetCurrency) {
        return amount * this.getConversionRate(targetCurrency);
    }
}
