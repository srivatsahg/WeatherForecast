package dinchi.org.weatherapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by H113585 on 4/12/2017.
 */

public class WeatherAdapter extends ArrayAdapter<WeatherModel> {

    private ArrayList<WeatherModel> weatherList;
    Context context;
    final String TAG = "WeatherAdapter";

    public WeatherAdapter(Context context, ArrayList<WeatherModel> weatherForecast){
        super(context,-1,weatherForecast);
        this.context = context;
        this.weatherList = weatherForecast;
        Log.i(TAG,"Inside WeatherAdapter Constructor");
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);

        Log.i(TAG,"Before layout inflate");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row_daywise, parent, false);

        Log.i(TAG,"After layout inflate");

        TextView textviewTemperature = (TextView)rowView.findViewById(R.id.txtForecastTemperature);
        TextView txtForecastHumidity = (TextView)rowView.findViewById(R.id.txtForecastHumidity);
        TextView txtForecastCondition = (TextView)rowView.findViewById(R.id.txtForecastCondition);
        TextView txtForecastPressure = (TextView)rowView.findViewById(R.id.txtForecastPressure);
        TextView txtForecastWind = (TextView)rowView.findViewById(R.id.txtForecastWind);

        textviewTemperature.setText(weatherList.get(position).getCurrentTemp());
        txtForecastHumidity.setText(weatherList.get(position).getHumidity());
        txtForecastCondition.setText(weatherList.get(position).getCondition());
        txtForecastPressure.setText(weatherList.get(position).getPressure());
        txtForecastWind.setText(weatherList.get(position).getWindSpeed());

        return rowView;
    }
}
