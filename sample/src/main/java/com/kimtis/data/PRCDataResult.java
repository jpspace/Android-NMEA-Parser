package com.kimtis.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by uiseok on 2016-09-22.
 */

public class PRCDataResult implements Serializable{
    @SerializedName("data")
    public RtcmGpsCorrV0[] rtcmResult;

    public com.ppsoln.domain.RtcmGpsCorrV0[] getRtcmResult(){
        com.ppsoln.domain.RtcmGpsCorrV0[] result = new com.ppsoln.domain.RtcmGpsCorrV0[rtcmResult.length];
        for(int i=0; i< rtcmResult.length;i++){
            result[i] = rtcmResult[i].getRtcmGpsCorrV0();
        }
        return result;
    }
}
