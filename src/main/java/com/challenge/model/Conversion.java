package com.challenge.model;


import java.time.LocalDateTime;

/**
 * Represents a conversion between two currencies, including the amount converted, currencies involved,
 * result of the conversion, target currency, and the time of conversion.
 */
public class Conversion {
    private final double amount;
    private final String currencyToConvert;
    private final double resultOfConversion;
    private final String targetCurrency;
    private final LocalDateTime conversionTime;

    public Conversion(double amount, String currencyToConvert, double resultOfConversion, String targetCurrency) {
        this.amount = amount;
        this.currencyToConvert = currencyToConvert;
        this.resultOfConversion = resultOfConversion;
        this.targetCurrency = targetCurrency;
        this.conversionTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("""
                \nThe amount of %.2f %s is equals to %.2f %s %n \n""", amount, currencyToConvert, resultOfConversion, targetCurrency);
    }
}
