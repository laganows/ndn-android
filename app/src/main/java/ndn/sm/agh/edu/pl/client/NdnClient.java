package ndn.sm.agh.edu.pl.client;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.named_data.jndn.Data;
import net.named_data.jndn.Face;
import net.named_data.jndn.Interest;
import net.named_data.jndn.Name;
import net.named_data.jndn.OnData;
import net.named_data.jndn.OnTimeout;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ndn.sm.agh.edu.pl.model.WeatherData;

import static ndn.sm.agh.edu.pl.utils.ApiContract.SERVER_URL;
import static ndn.sm.agh.edu.pl.utils.ApiContract.WEATHER_INTEREST_PREFIX;
import static ndn.sm.agh.edu.pl.utils.UnitConverter.convertKelvinsToCelcius;


public class NdnClient extends AsyncTask<Void, Void, String> {
    private boolean stopLoop = false;
    private String fetchedData;
    private String cityName;
    private TextView temperatureValue;
    private TextView minTemperatureValue;
    private TextView maxTemperatureValue;
    private TextView humidityValue;
    private TextView windSpeedValue;
    private TextView pressureValue;
    private TextView sunriseValue;
    private TextView sunsetValue;
    private TextView cloudyValue;

    public NdnClient(String cityName,
                     TextView temperatureValue,
                     TextView minTemperatureValue,
                     TextView maxTemperatureValue,
                     TextView humidityValue,
                     TextView windSpeedValue,
                     TextView pressureValue,
                     TextView sunriseValue,
                     TextView sunsetValue,
                     TextView cloudyValue) {
        this.cityName = cityName;
        this.temperatureValue = temperatureValue;
        this.minTemperatureValue = minTemperatureValue;
        this.maxTemperatureValue = maxTemperatureValue;
        this.humidityValue = humidityValue;
        this.windSpeedValue = windSpeedValue;
        this.pressureValue = pressureValue;
        this.sunriseValue = sunriseValue;
        this.sunsetValue = sunsetValue;
        this.cloudyValue = cloudyValue;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return fetchData();
    }

    @Override
    protected void onPostExecute(String fetchedData) {
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weatherResponse = null;
        try {
            weatherResponse = mapper.readValue(fetchedData, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (weatherResponse != null && weatherResponse.getData() != null) {
            updateViewWithResponse(weatherResponse.getData());
        }
    }

    private void updateViewWithResponse(WeatherData weatherData) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.temperatureValue.setText(Double.toString(convertKelvinsToCelcius(weatherData.getMain().getTemp())) + " °C");
        this.minTemperatureValue.setText(Double.toString(convertKelvinsToCelcius(weatherData.getMain().getTemp_min())) + " °C");
        this.maxTemperatureValue.setText(Double.toString(convertKelvinsToCelcius(weatherData.getMain().getTemp_max())) + " °C");
        this.humidityValue.setText(Double.toString(weatherData.getMain().getHumidity()) + " %");
        this.windSpeedValue.setText(Double.toString(weatherData.getWind().getSpeed()) + " m/s");
        this.pressureValue.setText(Double.toString(weatherData.getMain().getPressure()) + " hPa");
        this.sunriseValue.setText(dateFormat.format(new Date(Double.valueOf(weatherData.getSys().getSunrise()).longValue() * 1000)));
        this.sunsetValue.setText(dateFormat.format(new Date(Double.valueOf(weatherData.getSys().getSunset()).longValue() * 1000)));
        this.cloudyValue.setText(Double.toString(weatherData.getClouds().getAll()) + " %");
    }

    private String fetchData() {
        try {
            Face face = new Face(SERVER_URL);
            face.expressInterest(new Name(WEATHER_INTEREST_PREFIX + cityName + "/" + System.currentTimeMillis()),
                    new OnData() {
                        @Override
                        public void
                        onData(Interest interest, Data data) {
                            fetchedData = data.getContent().toString();
                            Log.i("RESPONSE", fetchedData);
                            stopLoop = true;
                        }
                    },
                    new OnTimeout() {
                        @Override
                        public void onTimeout(Interest interest) {
                            fetchedData = "ERROR: Timeout";
                            Log.i("ERROR", "Timeout");
                            stopLoop = true;
                        }
                    });

            while (!stopLoop) {
                face.processEvents();
                Thread.sleep(500);
            }
            face.shutdown();
            return fetchedData;
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}