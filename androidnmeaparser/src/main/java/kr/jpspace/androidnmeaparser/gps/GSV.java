package kr.jpspace.androidnmeaparser.gps;

import java.util.Arrays;
import java.util.List;

import kr.jpspace.androidnmeaparser.exception.NmeaMsgFormatException;

/**
 *
 */
public class GSV {

    /**
     *      GSV : Satellites in view
     *
     *      1) total number of messages
            2) message number
            3) satellites in view
            4) satellite number
            5) elevation in degrees
            6) azimuth in degrees to true
            7) SNR in dB
                more satellite infos like 4)-7)
            n) Checksum
     *
     */

    private String[] gsv;

    public GSV(String gsv){
        this.gsv = gsv.split(",");
    }

    public List<String> getGpgsvList()throws NmeaMsgFormatException {
        return Arrays.asList(gsv);
    }
}
