package fr.lernejo.prediction;

import java.util.List;

public class CountryTemperature {

    public final String country;
    public final List<Temperature> temperatures;

    public CountryTemperature(String country, List<Temperature> temperatures) {
        // TODO: add rules
        this.country = country;
        this.temperatures = temperatures;
    }

}
