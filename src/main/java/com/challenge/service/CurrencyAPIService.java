package com.challenge.service;


import com.challenge.utils.APIKeyManager;
import com.challenge.model.Currency;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Service class to interact with a currency exchange rate API and fetch currency data.
 */
public class CurrencyAPIService {

    private static final APIKeyManager apiKeyManager = APIKeyManager.getInstance();
    private static final String API_KEY = apiKeyManager.getApiKey();
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/";

    /**
     * Fetches currency data for a specific currency type from the API.
     *
     * @param currencyType The type of currency to fetch data for.
     * @return The Currency object containing the fetched data.
     */
    public Currency fetchData(String currencyType) {
        String address = API_URL + API_KEY + "/latest/" + currencyType;
        HttpResponse<String> response = this.sendRequest(address);
        return this.handleResponse(response);
    }

    /**
     * Sends an HTTP request to the specified address and retrieves the response.
     *
     * @param address The address to send the HTTP request to.
     * @return The HTTP response received.
     * @throws RuntimeException if an error occurs while sending the request.
     */
    public HttpResponse<String> sendRequest(String address) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Error sending the HTTP request.", e);
        }
    }

    /**
     * Handles the HTTP response received and converts it to a Currency object.
     *
     * @param response The HTTP response to handle.
     * @return The Currency object parsed from the response.
     */
    private Currency handleResponse(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error in the API request. Status code: " + response.statusCode());
        }
        return this.convertJsonToCurrency(response.body());
    }

    /**
     * Converts a JSON string to a Currency object using Gson.
     *
     * @param json The JSON string to convert.
     * @return The Currency object parsed from the JSON string.
     */
    private Currency convertJsonToCurrency(String json) {
        return new Gson().fromJson(json, Currency.class);
    }
}
