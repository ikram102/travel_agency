package fr.lernejo.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class APIRestController {

    @Autowired
    private final TemperatureService temperatureService;

    public APIRestController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @GetMapping("/temperature")
    public ResponseEntity<?> getCountryTemperatures(@RequestParam String country) {
        try {
            double countryTemperature = this.temperatureService.getTemperature(country);
            CountryTemperature response = new CountryTemperature(
                country,
                List.of(
                    new Temperature(LocalDate.now(), countryTemperature),
                    new Temperature(LocalDate.now(), countryTemperature)
                )
            );
            return ResponseEntity.ok(response);
        } catch (UnknownCountryException e) {
            return ResponseEntity.status(417).build();
        }
    }
}
