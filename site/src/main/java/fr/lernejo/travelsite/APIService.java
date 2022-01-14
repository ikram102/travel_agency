package fr.lernejo.travelsite;

import fr.lernejo.travelsite.models.CountryTemperature;
import fr.lernejo.travelsite.models.CountryTemperatures;
import fr.lernejo.travelsite.models.Inscription;
import fr.lernejo.travelsite.models.WeatherExpectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class APIService {

    @Autowired
    private final PredictionEngineClient predictionEngineClient;

    public final List<Inscription> inscriptions = new ArrayList<>();

    public final List<String> countries;

    public APIService(PredictionEngineClient predictionEngineClient) {
        this.predictionEngineClient = predictionEngineClient;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("countries.txt");
        try {
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            this.countries = content.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("countries.txt file not found.");
        }
    }

    public void registerUser(Inscription user) {
        if (!this.inscriptions.stream().map(inscription -> inscription.userName).collect(Collectors.toList()).contains(user.userName)
        ) {
            this.inscriptions.add(user);
        }
    }

    private CountryTemperature getMeanTemperature(CountryTemperatures countryTemperature) {
        return new CountryTemperature(
            countryTemperature.country,
            countryTemperature.temperatures.stream().mapToDouble(temp -> temp.temperature).average().orElseThrow()
        );
    }

    public List<CountryTemperature> getCountriesThatSatisfyExpectations(Inscription request) throws Exception {
        CountryTemperatures temperature = this.predictionEngineClient.listCountryTemperatures(request.userCountry).execute().body();
        CountryTemperature desiredCountryMeanTemperature = this.getMeanTemperature(temperature);
        return this.countries.stream().filter((String country) -> !Objects.equals(country, request.userCountry)).map((String country) -> {
                try {
                    CountryTemperatures temp = this.predictionEngineClient.listCountryTemperatures(country).execute().body();
                    return this.getMeanTemperature(temp);
                } catch (IOException e) {
                    return null;
                }
            }).filter(Objects::nonNull).filter((CountryTemperature countryTemperature) ->
                countryTemperature.temperature < (desiredCountryMeanTemperature.temperature - request.minimumTemperatureDistance) && request.weatherExpectation.equals(WeatherExpectation.COLDER) || (countryTemperature.temperature > (request.minimumTemperatureDistance + countryTemperature.temperature) && request.weatherExpectation.equals(WeatherExpectation.WARMER))
            ).collect(Collectors.toList());
    }

    public List<CountryTemperature> getTravels(String userName) {
        return this.inscriptions.stream().filter(inscription -> inscription.userName.equals(userName)).flatMap(inscription -> {
                try {
                    return getCountriesThatSatisfyExpectations(inscription).stream();
                } catch (Exception e) {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
