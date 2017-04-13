package dinchi.org.weatherapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by H113585 on 4/12/2017.
 */

public class WeatherForecast {
    private List<DayForecast> daysForecast = new ArrayList<DayForecast>();

    public void addForecast(DayForecast forecast) {
        daysForecast.add(forecast);
        System.out.println("Add forecast ["+forecast+"]");
    }

    public DayForecast getForecast(int dayNum) {
        return daysForecast.get(dayNum);
    }
}
