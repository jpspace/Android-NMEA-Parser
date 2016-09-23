package com.kimtis.data.constant;

/**
 * Created by uiseok on 2016-09-06.
 */
public class NetworkConstant {
    public static final String MY_SERVER = "http://192.168.0.91:8080";

    public interface TempConstant {

        public static final String SEARCH_URL = MY_SERVER + "/temp.php";

    }

    public interface PRCConstant{
        public static final String SEARCH_URL = MY_SERVER + "/api/Rtcm/Corrections";
    }

    public interface NavigationHeaderConstant{
        public static final String SEARCH_URL = MY_SERVER + "/api/Navigation/Header";
    }

}
