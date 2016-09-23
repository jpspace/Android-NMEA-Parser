package kr.jpspace.androidnmeaparser.gps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.jpspace.androidnmeaparser.exception.NmeaMsgFormatException;

/**
 *
 */
public class GSA {
    /*

        GSA : GPS DOP and active satellites

        1) Selection mode
        2) Mode
        3) ID of 1st satellite used for fix
        4) ID of 2nd satellite used for fix
        ...
        14) ID of 12th satellite used for fix
        15) PDOP in meters
        16) HDOP in meters
        17) VDOP in meters
        18) Checksum
    */
    private String[] gsa;

    public GSA(String gsa) {
        this.gsa = gsa.substring(7, gsa.length()).split(",");
    }

    public String getSelectionMode() throws NmeaMsgFormatException {
        return gsa[0];
    }

    public String getMode() throws NmeaMsgFormatException {
        return gsa[1];
    }

    public String getSatelliteUsedForFix(int number) throws NmeaMsgFormatException {
        /**
         *  get index from user for number 1st to 12th satellite used for fix
         */
        if (number < 1 || number > 12) {
            return null;
        } else {
            return gsa[number + 1];
        }
    }

    public List<Integer> getSatelliteListUsedInFix(){
        List<Integer> result = new ArrayList<Integer>();
        for(int i=2; i<14;i++){
            if(gsa[i] != null && !gsa[i].equals(""))
                result.add(Integer.parseInt(gsa[i]));
        }
        if(result.size()==0)
            return null;
        else
            return result;
    }

    public float getPDOPInMeters() throws NmeaMsgFormatException {
        return Float.valueOf(gsa[14]);
    }

    public float getHDOPInMeters() throws NmeaMsgFormatException {
        return Float.valueOf(gsa[15]);
    }

    public float getVDOPInMeters() throws NmeaMsgFormatException {
        return Float.valueOf(gsa[16]);
    }

    public String getCheckSum() throws NmeaMsgFormatException {
        return gsa[17];
    }


    public List<String> getGpgsaList() throws NmeaMsgFormatException {
        return Arrays.asList(gsa);
    }

}
