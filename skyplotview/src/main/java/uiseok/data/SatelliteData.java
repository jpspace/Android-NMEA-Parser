package uiseok.data;

import java.io.Serializable;

/**
 * Created by uiseok on 2016-09-16.
 */
public class SatelliteData implements Serializable {
    public float elevation_in_degree;
    public float azimuth_in_degree;
    public String satellite_num;
    public float snr;

    public SatelliteData(float elevation_in_degree, float azimuth_in_degree, String satellite_name, float snr) {
        this.elevation_in_degree = elevation_in_degree;
        this.azimuth_in_degree = azimuth_in_degree;
        this.satellite_num = satellite_name;
        this.snr = snr;
    }

    public static double getAzimuth_in_radian(double azimuth_in_degree) {
        return azimuth_in_degree * Math.PI / 180;
    }


}
