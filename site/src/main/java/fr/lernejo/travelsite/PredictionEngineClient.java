package fr.lernejo.travelsite;

import fr.lernejo.travelsite.models.CountryTemperatures;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

@Service
public interface PredictionEngineClient {
    @GET("/api/temperature")
    Call<CountryTemperatures> listCountryTemperatures(@Query("country") String country);
}
