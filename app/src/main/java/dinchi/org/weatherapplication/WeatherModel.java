package dinchi.org.weatherapplication;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VATSAG on 4/5/2017.
 */

public class WeatherModel {


    @SerializedName("windspeed")
    private String windSpeed;

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    @SerializedName("humidity")
    private String humidity;

    @SerializedName("pressure")
    private String pressure;

    @SerializedName("currtemp")
    private String currentTemp;

    @SerializedName("condition")
    private  String condition;

    @SerializedName("mintemp")
    private String minTemp;

    @SerializedName("maxtemp")
    private String maxTemp;


    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public String getCondition() {
        return condition;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }
//    public WeatherModel(String currentTemp, String minTemp, String maxTemp,
//                        String windSpeed,String pressure,String humidity,String condition){
//
//        this.currentTemp = currentTemp;
//        this.minTemp = minTemp;
//        this.maxTemp = maxTemp;
//        this.windSpeed = windSpeed;
//        this.pressure = pressure;
//        this.humidity = humidity;
//        this.condition = condition;
//    }

        public WeatherModel(){
    }
}


