package fr.lernejo.prediction;

import java.time.LocalDate;

public class Temperature {
    public final LocalDate date;
    public final double temperature;

    public Temperature(LocalDate date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }
}
