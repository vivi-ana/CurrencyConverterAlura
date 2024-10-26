package com.challenge;


import com.challenge.model.Conversion;
import com.challenge.model.ConversionStorage;
import com.challenge.model.Currency;
import com.challenge.service.CurrencyAPIService;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner keyboard = new Scanner(System.in);
    private static final Map<String, String> currencyMap = createCurrencyMap();

    public static void main(String[] args) {

        ConversionStorage conversionStorage = new ConversionStorage();

        int exit = 1;
        System.out.println("""
                ********************************************
                \nWELCOME TO THE CURRENCY CONVERTER
                """);

        while (exit != 0) {

            convertCurrency(conversionStorage);

            System.out.println("Please, enter 1 to continue converting or 0 to exit");
            exit = getValidatedInputExit();
        }
        keyboard.close();
        System.out.println("Thank you for using the currency converter. See you soon!");

        // Attempt to generate a file with the conversion data and handle any potential IOException.
        try {
            conversionStorage.generateFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("An error occurred while writing to the file.");
        }
    }

    /**
     * Retrieves and validates user input for an exit option, ensuring it is a valid integer.
     *
     * @return The validated exit option entered by the user.
     */
    private static int getValidatedInputExit() {
        while (true) {
            try {
                int input = keyboard.nextInt();
                if (input == 0 || input == 1) {
                    return input;
                } else {
                    System.out.println("Please enter either 0 to exit or 1 to continue converting.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                keyboard.next();
            }
        }
    }

    /**
     * Method to convert currency based on user inputs.
     *
     * @param conversionStorage The storage for conversion data.
     */
    public static void convertCurrency(ConversionStorage conversionStorage) {
        // Select the type of currency to convert.
        String currencyToConvert = getValidatedCurrency(currencyMap,"Select the type of currency you want to convert:");

        // Get the amount to convert.
        double amount = getValidatedConversionAmount(currencyToConvert);

        // Get currency data for the selected currency.
        Currency currency = getCurrencyData(currencyToConvert);

        // If currency data is available, proceed with the conversion.
        if (currency != null) {

            // Enter the currency code to convert to.
            String targetCurrency = getValidatedCurrency(currencyMap, "Enter the currency code you want to convert to:");

            // Calculate the result of the conversion.
            double resultOfConversion = currency.calculateConvertedAmount(amount, targetCurrency);

            // Create a Conversion object and display the result.
            Conversion conversion = new Conversion(amount, currencyToConvert, resultOfConversion, targetCurrency);
            System.out.printf(conversion.toString());

            // Add the conversion to the storage.
            conversionStorage.addConversion(conversion);
        }
    }

    /**
     * Prompts the user to enter the amount of currency they want to convert, ensuring it is a valid positive number.
     *
     * @param currencyToConvert The currency to be converted.
     * @return The validated conversion amount entered by the user.
     */
    public static double getValidatedConversionAmount(String currencyToConvert) {
        System.out.printf("""
                ********************************************
                How much of %s do you want to convert?
                ********************************************
                """, currencyToConvert);
        while (true) {
            try {
                double amount = Double.parseDouble(keyboard.next());
                if (amount > 0) {
                    return amount;
                } else {
                    System.out.println("Please enter a number greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Retrieves currency data based on the currency code.
     *
     * @param currencyToConvert The currency code.
     * @return The currency data.
     */
    public static Currency getCurrencyData(String currencyToConvert) {
        CurrencyAPIService service = new CurrencyAPIService();
        try {
            return service.fetchData(currencyToConvert);
        } catch (IllegalArgumentException e) {
            System.out.println("Error fetching currency data: " + e.getMessage());
            return null;
        }
    }

    /**
     * Prompts the user to select a currency code from a provided map of currencies and validates the input.
     *
     * @param currencyMap The map of currency codes and their descriptions.
     * @param prompt      The prompt message to display to the user.
     * @return The validated currency code selected by the user.
     */
    public static String getValidatedCurrency(Map<String, String> currencyMap, String prompt) {
        String currencyToConvert;
        do {
            printCurrencyMap(currencyMap, prompt);
            currencyToConvert = selectCurrency();
            if (isCurrencyNotValid(currencyMap, currencyToConvert)){
                System.out.println("Invalid currency, please enter a valid one.");
            }
        } while (isCurrencyNotValid(currencyMap, currencyToConvert));

        return currencyToConvert;

    }

    /**
     * Checks if a given currency code is not valid based on the provided currency map.
     *
     * @param currencyMap       The map of currency codes and their descriptions.
     * @param currencyToConvert The currency code to check for validity.
     * @return True if the currency code is not valid, false otherwise.
     */
    public static boolean isCurrencyNotValid(Map<String, String> currencyMap, String currencyToConvert){
        return !currencyMap.containsKey(currencyToConvert);
    }

    /**
     * Allows the user to select a currency code by entering it from the keyboard.
     *
     * @return The currency code entered by the user in uppercase.
     */
    public static String selectCurrency() {
        return keyboard.next().toUpperCase();
    }

    /**
     * Prints the currency selection menu.
     *
     * @param currencyMap The map containing currency codes as keys and their descriptions as values.
     * @param currencyPrompt The prompt message.
     */
    public static void printCurrencyMap(Map<String, String> currencyMap, String currencyPrompt) {
        System.out.printf("""
                ********************************************
                %s %n""", currencyPrompt);
        for (Map.Entry<String, String> entry : currencyMap.entrySet()) {
            System.out.printf("%-8s for %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println("********************************************");
    }

    public static Map<String, String> createCurrencyMap() {
        Map<String, String> currencyMap = new HashMap<>();
        currencyMap.put("ARS", "Argentine Pesos");
        currencyMap.put("BRL", "Brazilian Real");
        currencyMap.put("CAD", "Canadian Dollar");
        currencyMap.put("COP", "Colombian Pesos");
        currencyMap.put("EUR", "Euro");
        currencyMap.put("GBP", "Pound Sterling");
        currencyMap.put("MXN", "Mexican Pesos");
        currencyMap.put("USD", "US dollars");
        return currencyMap;
    }
}