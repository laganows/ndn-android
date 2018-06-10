package ndn.sm.agh.edu.pl.client;

import lombok.Data;
import ndn.sm.agh.edu.pl.model.WeatherData;

@Data
public class WeatherResponse {
    private String status;
    private WeatherData data;
}
