package dinchi.org.weatherapplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by H113585 on 4/12/2017.
 */

public class DayForecast {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public WeatherModel weather = new WeatherModel();
    public ForecastTemp forecastTemp = new ForecastTemp();
    public long timestamp;

    public class ForecastTemp {
        public float day;
        public float min;
        public float max;
        public float night;
        public float eve;
        public float morning;
    }

    public String getStringDate() {
        return sdf.format(new Date(timestamp));
    }
}
