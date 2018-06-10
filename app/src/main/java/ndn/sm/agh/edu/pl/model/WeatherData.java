package ndn.sm.agh.edu.pl.model;

import lombok.Data;

@Data
public class WeatherData {
    private String base;
    private double visibility;
    private long dt;
    private double id;
    private String name;
    private double cod;

    private Coordinates coord;
    private Weather [] weather;
    private Wind wind;
    private Clouds clouds;
    private SysStat sys;
    private MainStat main;
}
