package kr.jpspace.androidnmeaparser;

import kr.jpspace.androidnmeaparser.gps.GGA;
import kr.jpspace.androidnmeaparser.gps.GSA;
import kr.jpspace.androidnmeaparser.gps.GSV;
import kr.jpspace.androidnmeaparser.gps.RMC;

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

    public GSV getGSVData(String gsv){ return new GSV(gsv); }

    public RMC getRMCData(String rmc){return new RMC(rmc);}

}
