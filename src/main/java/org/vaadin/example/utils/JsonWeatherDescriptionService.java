package org.vaadin.example.utils;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.IOException;
import java.io.InputStream;

public class JsonWeatherDescriptionService {
    public JsonObject loadWeatherData() throws IOException {
        ClassLoader classLoader = JsonWeatherDescriptionService.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("WeatherDescriptions.json");
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        assert inputStream != null;
        inputStream.close();
        jsonReader.close();

        return jsonObject;
    }
}
