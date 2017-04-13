package dinchi.org.weatherapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static dinchi.org.weatherapplication.R.string.strHumidity;

/*
* Using the http://openweathermap.org/api
* Query the present day weather and Weather forecast
* api.openweathermap.org/data/2.5/weather?q=london&appid=b1b15e88fa797225412429c1c50c122a1
* http://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=b1b15e88fa797225412429c1c50c122a1
* T(°C) = T(K) - 273.15.
* */
public class MainScreenActivity extends AppCompatActivity {

    private final String CITY_NAME = "London,UK";
    private final String TAG = "WeatherSample";
    OkHttpClient client;
    String[] cities = {"London","Delhi","Bangalore","Berlin","Paris","California"};
    String API_KEY;
    private String strWindPrefix;
    private String strHumidityPrefix;
    private String selectedCity;
    private ListView lvWeather;
    private WeatherAdapter weatherAdapter;
    private ArrayList<WeatherModel> weatherList;

    /*
    * Text to show the current conditions
    * */
    private TextView txtCurrentTemperature;
    private TextView txtWindSpeed;
    private TextView txtHumidity;
    private TextView txtCondition;
    private ImageView imgCondition;                     //Can be anyone of the following SUNNY,CLOUDY,RAINY,SNOWY
    private Button btnGetWeatherDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        strWindPrefix = this.getString(R.string.strWind);
        strHumidityPrefix = this.getString(strHumidity);
        API_KEY = this.getString(R.string.weatherapikey);

        weatherList = new ArrayList<>();
        lvWeather = (ListView)findViewById(R.id.listWeatherForecast);

        Log.d(TAG, "Inside onCreate method");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,cities);

        //Getting the instance of AutoCompleteTextView
        final AutoCompleteTextView actv= (AutoCompleteTextView)findViewById(R.id.tvcities);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainScreenActivity.this,selectedCity,Toast.LENGTH_LONG).show();
            }
        });

        actv.setTextColor(Color.DKGRAY);

        txtCurrentTemperature = (TextView) findViewById(R.id.txtTemperature);
        txtHumidity = (TextView) findViewById(R.id.txtHumidityReading);
        txtWindSpeed = (TextView) findViewById(R.id.txtWindReading);
        txtCondition = (TextView) findViewById(R.id.txtCondition);
        btnGetWeatherDetails = (Button)findViewById(R.id.btnGetWeather);

        btnGetWeatherDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Inside Button Get weather handler");

                if (API_KEY.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
                    return;
                }

                client = new OkHttpClient();

//                JSONWeatherTask task = new JSONWeatherTask();
//                task.execute(new String[]{selectedCity});

                //Weather information
                getPresentWeatherCondition(selectedCity);

                //Weather Forecast
//                getWeatherForecast(selectedCity);
            }
        });
    }


//    private class JSONWeatherTask extends AsyncTask<String, Void, WeatherModel> {
//
//        @Override
//        protected WeatherModel doInBackground(String... params) {
//
//            final WeatherModel[] weatherdetails = new WeatherModel[1];
//            String city = params[0];
//
//            try {
//
//                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.openweathermap.org/data/2.5/weather?").newBuilder();
//                urlBuilder.addQueryParameter("q", city);
//                urlBuilder.addQueryParameter("appid", API_KEY);
//                String url = urlBuilder.build().toString();
//
//                Request request = new Request.Builder()
//                        .url(url)
//                        .build();
//
//                // Get a handler that can be used to post to the main thread
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    // Parse response using gson deserializer
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException {
//
//                        try {
//                            String responseData = response.body().string();
//
//                            JSONObject json = new JSONObject(responseData);
//
//                            String condition = "";
//                            String windSpeed = "";
//                            String currentTemp = "";
//                            String strHumidity = "";
//                            String strPressure = "";
//                            String strMinTemp = "";
//                            String strMaxTemp = "";
//
//                            JSONArray weather = jsonHelperGetJSONArray(json, "weather");
//                            if (weather != null) {
//                                for (int i = 0; i < weather.length(); i++) {
//                                    JSONObject thisWeather = weather.getJSONObject(i);
//                                    condition = jsonHelperGetString(thisWeather, "main");
//                                }
//                            }
//
//                            JSONObject mainJson = json.getJSONObject("main");
//                            if (mainJson != null) {
//
//                                currentTemp = KelvinToCentigrade(jsonHelperGetString(mainJson, "temp"));
//                                strHumidity = jsonHelperGetString(mainJson, "humidity");
//                                strPressure = jsonHelperGetString(mainJson, "pressure");
//                                strMinTemp = KelvinToCentigrade(jsonHelperGetString(mainJson, "temp_min"));
//                                strMaxTemp = KelvinToCentigrade(jsonHelperGetString(mainJson, "temp_max"));
//                            }
//
//                            JSONObject windjson = json.getJSONObject("wind");
//                            if (windjson != null) {
//                                windSpeed = jsonHelperGetString(windjson, "speed");
//                            }
//
//                    /*
//                    * WeatherModel keeps
//                    * */
//                            WeatherModel weatherModel =
//                                    new WeatherModel();
//                            weatherModel.setCurrentTemp(currentTemp);
//                            weatherModel.setMinTemp(strMinTemp);
//                            weatherModel.setMaxTemp(strMaxTemp);
//                            weatherModel.setWindSpeed(windSpeed);
//                            weatherModel.setPressure(strPressure);
//                            weatherModel.setHumidity(strHumidity);
//                            weatherModel.setCondition(condition);
//                            weatherdetails[0] = weatherModel;
//
//                            // Access deserialized user object here
//                            Log.i("TAG", "Temp : " + currentTemp);
//                            Log.i("TAG", "Humidity : " + strHumidity);
//                            Log.i("TAG", "Pressure : " + strPressure);
//                            Log.i("TAG", "Wind Speed : " + windSpeed);
//                            Log.i("TAG", "Min Temp : " + strMinTemp);
//                            Log.i("TAG", "Max Temp : " + strMaxTemp);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            //return the details
//            return weatherdetails[0];
//        }
//
//
//        @Override
//        protected void onPostExecute(WeatherModel weatherModel) {
//            super.onPostExecute(weatherModel);
//
//            if (weatherModel != null) {
//
//                Log.i("POST-EXE", "Temp : " + weatherModel.getCurrentTemp());
//                Log.i("POST-EXE", "Humidity : " + weatherModel.getHumidity());
//                Log.i("POST-EXE", "Pressure : " + weatherModel.getPressure());
//                Log.i("POST-EXE", "Wind Speed : " + weatherModel.getWindSpeed());
//                Log.i("POST-EXE", "Min Temp : " + weatherModel.getMinTemp());
//                Log.i("POST-EXE", "Max Temp : " + weatherModel.getMaxTemp());
//
//                // Run view-related code back on the main thread
//                final String finalCurrentTemp = weatherModel.getCurrentTemp() + "°C";
//                final String finalHumidity = strHumidityPrefix + " : " + weatherModel.getHumidity() + "%";
//                final String finalWindSpeed = strWindPrefix + " : " + weatherModel.getWindSpeed() + " m/s";
//                final String finalCondition = weatherModel.getCondition();
//
//                Log.i("POST-EXEC", "Temp : " + finalCurrentTemp);
//                Log.i("POST-EXEC", "Humidity : " + finalHumidity);
//                Log.i("POST-EXEC", "Wind Speed : " + finalWindSpeed);
//                Log.i("POST-EXEC", "Condition : " + finalCondition);
//
//                txtCurrentTemperature.setText(finalCurrentTemp);
//                txtHumidity.setText(finalHumidity);
//                txtWindSpeed.setText(finalWindSpeed);
//                txtCondition.setText(finalCondition);
//            }
//        }
//    }


    /*
    * Gets the present weather condition
    * */
        private void getPresentWeatherCondition(String city) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.openweathermap.org/data/2.5/weather?").newBuilder();
        urlBuilder.addQueryParameter("q", city);
        urlBuilder.addQueryParameter("appid", API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            // Parse response using gson deserializer
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    String responseData = response.body().string();

                    JSONObject json = new JSONObject(responseData);

                    /*
                    * String windSpeed;
                      String humidity; -
                      String pressure; -
                      String currentTemp; -
                      String condition;
                      String minTemp; -
                      String maxTemp; -
                    * */

                    String condition = "";
                    String windSpeed = "";
                    String currentTemp = "";
                    String strHumidity = "";
                    String strPressure = "";
                    String strMinTemp = "";
                    String strMaxTemp = "";

                    JSONArray weather = jsonHelperGetJSONArray(json, "weather");
                    if (weather != null) {
                        for (int i = 0; i < weather.length(); i++) {
                            JSONObject thisWeather = weather.getJSONObject(i);
                            condition = jsonHelperGetString(thisWeather, "main");
                        }
                    }

                    JSONObject mainJson = json.getJSONObject("main");
                    if (mainJson != null) {

                        currentTemp = KelvinToCentigrade(jsonHelperGetString(mainJson, "temp"));
                        strHumidity = jsonHelperGetString(mainJson, "humidity");
                        strPressure = jsonHelperGetString(mainJson, "pressure");
                        strMinTemp = KelvinToCentigrade(jsonHelperGetString(mainJson, "temp_min"));
                        strMaxTemp = KelvinToCentigrade(jsonHelperGetString(mainJson, "temp_max"));
                    }

                    JSONObject windjson = json.getJSONObject("wind");
                    if (windjson != null) {
                        windSpeed = jsonHelperGetString(windjson, "speed");
                    }

                    /*
                    * WeatherModel keeps
                    * */
                    WeatherModel weatherModel =
                            new WeatherModel();
                    weatherModel.setCurrentTemp(currentTemp);
                    weatherModel.setMinTemp(strMinTemp);
                    weatherModel.setMaxTemp(strMaxTemp);
                    weatherModel.setWindSpeed(windSpeed);
                    weatherModel.setPressure(strPressure);
                    weatherModel.setHumidity(strHumidity);
                    weatherModel.setCondition(condition);

                    // Access deserialized user object here
                    Log.i("TAG", "Temp : " + currentTemp);
                    Log.i("TAG", "Humidity : " + strHumidity);
                    Log.i("TAG", "Pressure : " + strPressure);
                    Log.i("TAG", "Wind Speed : " + windSpeed);
                    Log.i("TAG", "Min Temp : " + strMinTemp);
                    Log.i("TAG", "Max Temp : " + strMaxTemp);

                    // Run view-related code back on the main thread
                    final String finalCurrentTemp = currentTemp + "°C";
                    final String finalHumidity = strHumidityPrefix + " : " + strHumidity + "%";
                    final String finalWindSpeed = strWindPrefix + " : " + windSpeed + " m/s";
                    final String finalCondition = condition;

                    MainScreenActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtCurrentTemperature.setText(finalCurrentTemp);
                            txtHumidity.setText(finalHumidity);
                            txtWindSpeed.setText(finalWindSpeed);
                            txtCondition.setText(finalCondition);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /*
    * Gets the 10-day forecast for the City
    * */
    private void getWeatherForecast(String city) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.openweathermap.org/data/2.5/forecast?").newBuilder();
        urlBuilder.addQueryParameter("q", city);
        urlBuilder.addQueryParameter("appid", API_KEY);
        String url = urlBuilder.build().toString();

        Log.i("FORECAST","URL : " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                /*
                * Response is JSON
                *
                {"city":{"id":1851632,"name":"Shuzenji",
                "coord":{"lon":138.933334,"lat":34.966671},
                "country":"JP",
                "cod":"200",
                "message":0.0045,
                "cnt":38,
                "list":[{
                        "dt":1406106000,
                        "main":{
                            "temp":298.77,
                            "temp_min":298.77,
                            "temp_max":298.774,
                            "pressure":1005.93,
                            "sea_level":1018.18,
                            "grnd_level":1005.93,
                            "humidity":87
                            "temp_kf":0.26},
                        "weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],
                        "clouds":{"all":88},
                        "wind":{"speed":5.71,"deg":229.501},
                        "sys":{"pod":"d"},
                        "dt_txt":"2014-07-23 09:00:00"}
                        ]}
                * */

                try{
                    String responseData = response.body().string();
                    Log.i("FORECAST","Response : " + responseData);

                    JSONObject json = new JSONObject(responseData);
                    JSONArray jsonForecastArray = jsonHelperGetJSONArray(json, "list");
                    if(jsonForecastArray != null){
                        for (int i = 0; i < jsonForecastArray.length(); i++) {
                            JSONObject jsonobject = jsonForecastArray.getJSONObject(i);

                            if(jsonobject != null)
                            {
                                String currentTemperature="";
                                String minTemp="";
                                String maxTemp="";
                                String pressure="";
                                String humidity="";
                                String condition="";
                                String windSpeed="";

                                JSONObject mainjson = jsonHelperGetJSONObject(jsonobject,"main");
                                if(mainjson != null){

                                    currentTemperature = KelvinToCentigrade(jsonHelperGetString(mainjson,"temp"));
                                    minTemp = KelvinToCentigrade(jsonHelperGetString(mainjson,"temp_min"));
                                    maxTemp = KelvinToCentigrade(jsonHelperGetString(mainjson,"temp_max"));
                                    pressure = jsonHelperGetString(mainjson,"pressure");
                                    humidity = jsonHelperGetString(mainjson,"humidity");

                                }

//                                JSONArray weatherArray = jsonHelperGetJSONArray(json, "weather");
//                                if (weatherArray != null) {
//                                    for (int j = 0; j < weatherArray.length(); j++) {
//                                        JSONObject thisWeather = weatherArray.getJSONObject(i);
//                                        if(thisWeather != null){
//                                            condition = jsonHelperGetString(thisWeather,"main");
//                                        }
//                                    }
//                                }

                                condition = "Sunny";

                                JSONObject jsonWind = jsonHelperGetJSONObject(jsonobject,"wind");
                                if(jsonWind != null){
                                    windSpeed = jsonHelperGetString(jsonWind,"speed");
                                }

                                Log.i("FORECAST","Temperature : " + currentTemperature);
                                Log.i("FORECAST","Min. Temperature : " + minTemp);
                                Log.i("FORECAST","Max. Temperature : " + maxTemp);
                                Log.i("FORECAST","Pressure : " + pressure);
                                Log.i("FORECAST","Humidity : " + humidity);
                                Log.i("FORECAST","Condition : " + condition);
                                Log.i("FORECAST","Wind Speed : " + windSpeed);

                                /*
                                * Add all the forecast to the arraylist
                                * */
                                WeatherModel model = new WeatherModel();
                                model.setCondition(currentTemperature);
                                weatherList.add(model);

                                Log.d("FORECAST","Added Weather Details");
                            }
                        }
                    }

//                    MainScreenActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            WeatherAdapter adapter = new WeatherAdapter(MainScreenActivity.this,weatherList);
//                            lvWeather.setAdapter(adapter);
//                        }
//                    });
                }
                catch (JSONException jex){
                    jex.printStackTrace();
                }
            }
        });
    }

    private String jsonHelperGetString(JSONObject obj, String k) {
        String v = null;
        try {
            v = obj.getString(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }

    private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k) {
        JSONObject o = null;

        try {
            o = obj.getJSONObject(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return o;
    }

    private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k) {
        JSONArray a = null;

        try {
            a = obj.getJSONArray(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return a;
    }

    private String KelvinToCentigrade(String kelvin) {
//        return String.valueOf(Double.valueOf(kelvin) - 273.15);
        return String.format("%.1f", Double.valueOf(kelvin) - 273.15);
    }
}
