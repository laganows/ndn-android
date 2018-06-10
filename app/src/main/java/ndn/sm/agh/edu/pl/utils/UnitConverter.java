package ndn.sm.agh.edu.pl.utils;

public class UnitConverter {

    public static double convertKelvinsToCelcius(double temperatureInKelvins) {
        return roundNumberToTwoDecimals(temperatureInKelvins - 273.15);
    }

    private static double roundNumberToTwoDecimals(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}
