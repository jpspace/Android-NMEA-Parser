package uiseok.data;

import java.io.Serializable;

/**
 * Created by uiseok on 2016-09-16.
 */
public class SatelliteData implements Serializable {
    public double elevation_in_degree;
    public double azimuth_in_degree;
    public String satellite_num;
    public float snr;

    public SatelliteData(double elevation_in_degree, double azimuth_in_degree, String satellite_num, float snr) {
        this.elevation_in_degree = elevation_in_degree;
        this.azimuth_in_degree = azimuth_in_degree;
        this.satellite_num = satellite_num;
        this.snr = snr;
    }

    public SatelliteData(double elevation_in_degree, double azimuth_in_degree, String satellite_num){
        this.elevation_in_degree = elevation_in_degree;
        this.azimuth_in_degree = azimuth_in_degree;
        this.satellite_num = satellite_num;
    }

    public static double getAzimuth_in_radian(double azimuth_in_degree) {
        return azimuth_in_degree * Math.PI / 180;
    }


}