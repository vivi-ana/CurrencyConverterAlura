package com.challenge.model;

import com.challenge.utils.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to store and manage a list of Conversion objects and generate a JSON file with the conversion history.
 */
public class ConversionStorage {
    private final List<Conversion> conversionList = new ArrayList<>();

    public void addConversion (Conversion conversion) {
        this.conversionList.add(conversion);
    }

    public void generateFile() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        try (FileWriter file = new FileWriter("conversionHistory.json")) {
            file.write(gson.toJson(conversionList));
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file " + e.getMessage());
            throw e;
        }
    }
}
