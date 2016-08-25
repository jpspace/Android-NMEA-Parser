package kr.jpspace.androidnmeaparser.gps;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class GPGGA {
    /*
        GPGGA = Global Positioning System Fix Data

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
    private String[] gpgga;

    private float utc;
    private float latitude;
    private String latDirection;
    private float longitude;
    private String lonDirection;
    private int gps_quality_indicator;
    private int num_of_satellites;
    private float hdp;
    private float altitude;
//    private String




    public GPGGA(String gpgga) {
        this.gpgga = gpgga.split(",");
        utc = Float.valueOf(this.gpgga[1]);
        latitude = Float.valueOf(this.gpgga[2]);
        latDirection = this.gpgga[3];
        longitude = Float.valueOf(this.gpgga[4]);
        lonDirection = this.gpgga[5];
        gps_quality_indicator = Integer.valueOf(this.gpgga[6]);

    }

    public String[] getGpggaArray(){
        return gpgga;
    }

    public List<String> getGpggaList(){
        return Arrays.asList(gpgga);
    }


}
