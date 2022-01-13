package fr.lernejo.travelsite;

import fr.lernejo.travelsite.models.CountryTemperature;
import fr.lernejo.travelsite.models.Inscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class APIRestController {

    @Autowired
    private final APIService apiService;

    public APIRestController(APIService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/inscription")
    public ResponseEntity<?> register(@RequestBody Inscription body) {
        this.apiService.registerUser(body);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/travels")
    public ResponseEntity<?> getTravels(@RequestParam String userName) {
        List<CountryTemperature> travels = this.apiService.getTravels(userName);
        return ResponseEntity.ok(travels);
    }

}
