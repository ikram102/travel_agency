package fr.lernejo.travelsite.models;

public class Inscription {

    public final String userEmail;
    public final String userName;
    public final String userCountry;
    public final WeatherExpectation weatherExpectation;
    public final Integer minimumTemperatureDistance;

    public Inscription() {
        this.userEmail = null;
        this.userName = null;
        this.userCountry = null;
        this.weatherExpectation = null;
        this.minimumTemperatureDistance = null;    }

    public Inscription(
        String userEmail,
        String userName,
        String userCountry,
        WeatherExpectation weatherExpectation,
        Integer minimumTemperatureDistance
    ) {
        // TODO: add rules
        this.userEmail = userEmail;
        this.userName = userName;
        this.userCountry = userCountry;
        this.weatherExpectation = weatherExpectation;
        this.minimumTemperatureDistance = minimumTemperatureDistance;
    }

}
