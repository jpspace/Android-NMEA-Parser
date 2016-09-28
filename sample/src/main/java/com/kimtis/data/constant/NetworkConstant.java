package com.kimtis.data.constant;

/**
 * Created by uiseok on 2016-09-06.
 */
public class NetworkConstant {
    public static final String MY_SERVER = "http://222.112.13.100:33061";

    public interface TempConstant {

        public static final String SEARCH_URL = MY_SERVER + "/temp.php";

    }

    public interface PRCConstant{
        public static final String SEARCH_URL = MY_SERVER + "/api/Rtcm/Corrections";
    }

    public interface NavigationHeaderConstant{
        public static final String SEARCH_URL = MY_SERVER + "/api/Navigation/Header";
    }

    public interface  NavigationDataConstant{
        public static final String SEARCH_URL = MY_SERVER + "/api/Navigation/Data/All";
    }

}
