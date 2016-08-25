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
        this.gga = gga.split(",");
    }

    public float getUtc() throws NmeaMsgFormatException {
        return Float.valueOf(this.gga[1]);
    }

    public float getLatitude() throws NmeaMsgFormatException {
        return Float.valueOf(this.gga[2]);
    }

    public String getLatDirection() throws NmeaMsgFormatException {
        return this.gga[3];
    }

    public float getLongitude()throws NmeaMsgFormatException {
        return Float.valueOf(this.gga[4]);
    }

    public String getLonDirection() throws NmeaMsgFormatException {
        return this.gga[5];
    }

    public int getGpsQualityIndicator()throws NmeaMsgFormatException {
        return Integer.valueOf(this.gga[6]);
    }

    public int getNumOfSatellites() throws NmeaMsgFormatException {
        return Integer.valueOf(this.gga[7]);
    }

    public float getHdp()throws NmeaMsgFormatException {
        return Float.valueOf(this.gga[8]);
    }

    public float getAltitude()throws NmeaMsgFormatException {
        return Float.valueOf(this.gga[9]);
    }

    public String getAltitudeUnitMeter() throws NmeaMsgFormatException {
        return this.gga[10];
    }

    public float getDiffBetweenWgs84NGeoid()throws NmeaMsgFormatException {
        return Float.valueOf(this.gga[11]);
    }

    public String getGeoidalSeperationMeter() throws NmeaMsgFormatException {
        return this.gga[12];
    }

    public float getDgpsUpdateTime()throws NmeaMsgFormatException {
        return Float.valueOf(this.gga[13]);
    }

    public String getDgpsStationId()throws NmeaMsgFormatException {
        return this.gga[14];
    }

    public String[] getGpggaArray() {
        try {
            return gga;
        } catch (Exception e) {
            throw new NmeaMsgFormatException();
        }
    }

    public List<String> getGpggaList()throws NmeaMsgFormatException {
        return Arrays.asList(gga);
    }


}
