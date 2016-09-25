package kr.jpspace.androidnmeaparser.gps;

import java.util.Arrays;
import java.util.List;

import kr.jpspace.androidnmeaparser.exception.NmeaMsgFormatException;

/**
 *
 */
public class RMC {

    private String[] rmc;

    public RMC(String rmc){
        this.rmc = rmc.substring(7, rmc.length()).split(",");
    }

    public List<String> getGprmcList()throws NmeaMsgFormatException {
        return Arrays.asList(rmc);
    }

    public String getTimeOfFixUTC() throws  NmeaMsgFormatException{
        return rmc[0];
    }

    public String getDateOfFix() throws  NmeaMsgFormatException{
        return rmc[6];
    }

}
