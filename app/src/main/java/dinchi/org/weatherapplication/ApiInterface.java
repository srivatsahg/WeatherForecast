package dinchi.org.weatherapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Srivatsa on 4/5/2017.
 */

public interface ApiInterface {

    @GET("weather")
    Call<WeatherModel> getCurrentWeather(@Query("city") String cityname, @Query("api_key") String apiKey);

    @GET("forecast")
    Call<List<WeatherModel>> getForecast(@Query("city") String cityname, @Query("api_key") String apiKey);
}
