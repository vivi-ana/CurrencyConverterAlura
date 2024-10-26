package com.challenge.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TypeAdapter for LocalDateTime objects to handle serialization and deserialization to/from JSON.
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        out.value(formatter.format(value));
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        return LocalDateTime.parse(in.nextString(), formatter);
    }
}