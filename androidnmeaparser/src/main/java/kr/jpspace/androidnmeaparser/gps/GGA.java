package kr.jpspace.androidnmeaparser.gps;

import java.util.Arrays;
import java.util.List;

import kr.jpspace.androidnmeaparser.exception.NmeaMsgFormatException;

/**
 *
 */
public class GGA {
    /*
        GGA = Global Positioning System Fix Data Time, Position and fix related data for a GPS receiver

        1) Time (UTC)
        2) Latitude
        3) N or S (North or South)
        4) Longitude
        5) E or W (East or West)
        6) GPS Quality Indicator,
            0 - fix not available,
            1 - GPS fix,
            2 - Differential GPS fix
        7) Number of satellites in view, 00 - 12
        8) Horizontal Dilution of precision
        9) Antenna Altitude above/below mean-sea-level (geoid)
        10) Units of antenna altitude, meters
        11) Geoidal separation, the difference between the WGS-84 earth
            ellipsoid and mean-sea-level (geoid), "-" means mean-sea-level below ellipsoid
        12) Units of geoidal separation, meters
        13) Age of differential GPS data, time in seconds since last SC104
            type 1 or 9 update, null field when DGPS is not used
        14) Differential reference station ID, 0000-1023
        15) Checksum
     */
    private String[] gga;

    public GGA(String gga) {
        this.gga = gga.substring(7, gga.length()).split(",");
    }

    public String getUtcString() throws NmeaMsgFormatException {
        return this.gga[0];
    }

    public double getLatitude() throws NmeaMsgFormatException {
        String lat = this.gga[1];
        double degree = Double.parseDouble(lat.substring(0, 2));
        double minute = Double.parseDouble(lat.substring(2, lat.length()));
        return degree + minute / 60;
    }

    public String getLatDirection() throws NmeaMsgFormatException {
        return this.gga[2];
    }

    public double getLongitude() throws NmeaMsgFormatException {
        String lon = this.gga[3];
        double degree = Double.parseDouble(lon.substring(0, 3));
        double minute = Double.parseDouble(lon.substring(3, lon.length()));
        return degree + minute / 60;
    }

    public String getLonDirection() throws NmeaMsgFormatException {
        return this.gga[4];
    }

    public int getGpsQualityIndicator() throws NmeaMsgFormatException {
        return Integer.valueOf(this.gga[5]);
    }

    public int getNumOfSatellites() throws NmeaMsgFormatException {
        return Integer.valueOf(this.gga[6]);
    }

    public double getHdp() throws NmeaMsgFormatException {
        return Double.parseDouble(this.gga[7]);
    }

    public double getAltitude() throws NmeaMsgFormatException {
        return Double.parseDouble(this.gga[8])+Double.parseDouble(this.gga[10]);
    }

    public String getAltitudeUnitMeter() throws NmeaMsgFormatException {
        return this.gga[9];
    }

    public double getDiffBetweenWgs84NGeoid() throws NmeaMsgFormatException {
        return Double.parseDouble(this.gga[10]);
    }

    public String getGeoidalSeperationMeter() throws NmeaMsgFormatException {
        return this.gga[11];
    }

    public double getDgpsUpdateTime() throws NmeaMsgFormatException {
        return Double.parseDouble(this.gga[12]);
    }

    public String getDgpsStationId() throws NmeaMsgFormatException {
        return this.gga[13];
    }

    public String[] getGpggaArray() {
        try {
            return gga;
        } catch (Exception e) {
            throw new NmeaMsgFormatException();
        }
    }

    public List<String> getGpggaList() throws NmeaMsgFormatException {
        return Arrays.asList(gga);
    }


}
