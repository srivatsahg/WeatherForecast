package dinchi.org.weatherapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by H113585 on 4/5/2017.
 */

public class WeatherAPI {

    private static final String CURRENT_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(CURRENT_WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
