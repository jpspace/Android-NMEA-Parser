package kr.jpspace.androidnmeaparser;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Nmea {


    private Map<String,Integer> indexMap;
    private String[] nmeaArray;
    private String nmea;
    public Nmea(String nmea) {
        this.nmea = nmea;
        indexMap = new HashMap<String,Integer>();
        nmeaArray = nmea.split("\r\n");
        for(int i=0; i<nmeaArray.length; i++){
            String temp = nmeaArray[i].substring(1,6);
            if(!indexMap.containsKey(temp)){
                indexMap.put(temp, i);
            }
        }
    }

    @Override
    public String toString() {
        return nmea;
    }

    public String getData(String type){
        return nmeaArray[indexMap.get(type)];
    }

    public String getKeys(){
        return indexMap.keySet().toString();
    }

}
