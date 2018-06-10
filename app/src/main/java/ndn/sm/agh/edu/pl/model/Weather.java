package ndn.sm.agh.edu.pl.model;

import lombok.Data;

@Data
public class Weather {
    private double id;
    private String main;
    private String description;
    private String icon;
}
