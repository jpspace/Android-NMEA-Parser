package kr.jpspace.androidnmeaparser;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Nmea {
//    private String GPGGAString = "";
//    private String GPGSVString = "";
//    private String GPRMCString = "";
//    private String GPGSAString = "";

    private Map<String,Integer> indexMap;
    private String[] nmeaArray;

    public Nmea(String nmea) {
        indexMap = new HashMap<String,Integer>();
        nmeaArray = nmea.split("\r\n");
//        for (String s : nmeaArray) {
//            switch (s.substring(1, 6)) {
//                case "GPGGA":
//                    GPGGAString += s + "\n";
//                    break;
//                case "GPGSV":
//                    GPGSVString += s + "\n";
//                    break;
//                case "GPRMC":
//                    GPRMCString += s + "\n";
//                    break;
//                case "GPGSA":
//                    GPGSAString += s + "\n";
//                    break;
//            }
//        }
        for(int i=0; i<nmeaArray.length; i++){
            String temp = nmeaArray[i].substring(1,6);
            if(!indexMap.containsKey(temp)){
                indexMap.put(temp, i);
            }
        }
    }

    public String getData(String type){
        return nmeaArray[indexMap.get(type)];
    }

    public String getKeys(){
        return indexMap.keySet().toString();
    }

}
