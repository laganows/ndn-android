package ndn.sm.agh.edu.pl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ndn.sm.agh.edu.pl.client.NdnClient;
import ndn.sm.agh.edu.pl.ndn_android.R;

public class MainActivity extends AppCompatActivity {
    private EditText cityName;
    private Button checkWeatherButton;
    private TextView temperatureValue;
    private TextView minTemperatureValue;
    private TextView maxTemperatureValue;
    private TextView humidityValue;
    private TextView windSpeedValue;
    private TextView pressureValue;
    private TextView sunriseValue;
    private TextView sunsetValue;
    private TextView cloudyValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkWeatherButton = findViewById(R.id.checkWeatherButton);
        cityName = findViewById(R.id.cityName);
        temperatureValue = findViewById(R.id.temperatureValue);
        minTemperatureValue = findViewById(R.id.minTemperatureValue);
        maxTemperatureValue = findViewById(R.id.maxTemepratureValue);
        humidityValue = findViewById(R.id.humidityValue);
        windSpeedValue = findViewById(R.id.windSpeedValue);
        pressureValue = findViewById(R.id.pressureValue);
        sunriseValue = findViewById(R.id.sunriseValue);
        sunsetValue = findViewById(R.id.sunsetValue);
        cloudyValue = findViewById(R.id.cloudyValue);
        addListenerForCheckWeatherButton();
    }

    private void addListenerForCheckWeatherButton() {
        checkWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NdnClient(cityName.getText().toString(),
                        temperatureValue,
                        minTemperatureValue,
                        maxTemperatureValue,
                        humidityValue,
                        windSpeedValue,
                        pressureValue,
                        sunriseValue,
                        sunsetValue,
                        cloudyValue).execute();
            }
        });
    }
}
