package kr.jpspace.androidnmeaparser.gps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.jpspace.androidnmeaparser.SatelliteData;
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

        this.gsv = gsv.substring(7, gsv.length()).split(",");

    }

    public String getTotalNumberOfMessages(){
        return gsv[0];
    }

    public String getMessageNumber(){
        return gsv[1];
    }

    public int getTotalSatelliteNumbersInView(){
        return Integer.parseInt(gsv[2]);
    }

    public List<SatelliteData> getSatelliteListData(){
        List<SatelliteData> result = new ArrayList<SatelliteData>();
        try {
            SatelliteData item = new SatelliteData();
            item.setPrn(gsv[3]);
            item.setSnr((int) Double.parseDouble(gsv[6]));
            result.add(item);
            item = new SatelliteData();
            item.setPrn(gsv[7]);
            item.setSnr((int) Double.parseDouble(gsv[10]));
            result.add(item);
            item = new SatelliteData();
            item.setPrn(gsv[11]);
            item.setSnr((int) Double.parseDouble(gsv[14]));
            result.add(item);
            item = new SatelliteData();
            item.setPrn(gsv[15]);
            item.setSnr((int) Double.parseDouble(gsv[18].substring(0, gsv[18].indexOf('*'))));
            result.add(item);
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public int[] getSnrArray(){
        int[] result = new int[4];
        try{ result[0] =     (int)Double.parseDouble(gsv[6]);}catch(Exception e){ result[0] = 0; }
        try{ result[1] =     (int)Double.parseDouble(gsv[10]);}catch(Exception e){ result[1] = 0; }
        try{ result[2] =     (int)Double.parseDouble(gsv[14]);}catch(Exception e){ result[2] = 0; }
        try{ result[3] =     (int)Double.parseDouble(gsv[18]);}catch(Exception e){ result[3] = 0; }
        return result;
    }

    public List<String> getGpgsvList()throws NmeaMsgFormatException {
        return Arrays.asList(gsv);
    }
}
