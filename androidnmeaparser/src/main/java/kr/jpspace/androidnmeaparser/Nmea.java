package kr.jpspace.androidnmeaparser;

/**
 *
 */
public class Nmea {
    private String GPGGA = "";
    private String GPGSV = "";
    private String GPRMC = "";
    private String GPGSA = "";

    public Nmea(String nmea) {
        String[] nmeaArray = nmea.split("\r\n");
        for (String s : nmeaArray) {
            switch (s.substring(1, 6)) {
                case "GPGGA":
                    GPGGA += s + "\n";
                    break;
                case "GPGSV":
                    GPGSV += s + "\n";
                    break;
                case "GPRMC":
                    GPRMC += s + "\n";
                    break;
                case "GPGSA":
                    GPGSA += s + "\n";
                    break;
            }
        }
    }
}
