package fr.lernejo.travelsite.models;

import java.util.List;

public class CountryTemperatures {

    public final String country;
    public final List<Temperature> temperatures;

    public CountryTemperatures(String country, List<Temperature> temperatures) {
        // TODO: add rules
        this.country = country;
        this.temperatures = temperatures;
    }

}
