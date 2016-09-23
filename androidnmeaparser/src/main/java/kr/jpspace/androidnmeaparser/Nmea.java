package kr.jpspace.androidnmeaparser;

import kr.jpspace.androidnmeaparser.gps.GGA;
import kr.jpspace.androidnmeaparser.gps.GSA;

/**
 *
 */
public class Nmea {


    private static Nmea instance;

    public static Nmea getInstnace() {
        if (instance == null) {
            instance = new Nmea();
        }
        return instance;
    }

    public GGA getGGAData(String gga){
        return new GGA(gga);
    }

    public GSA getGSAData(String gsa){
        return new GSA(gsa);
    }


}
