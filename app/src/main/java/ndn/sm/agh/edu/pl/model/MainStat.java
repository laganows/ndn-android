package ndn.sm.agh.edu.pl.model;

import lombok.Data;

@Data
public class MainStat {
    private double temp;
    private double pressure;
    private double humidity;
    private double temp_min;
    private double temp_max;
}
