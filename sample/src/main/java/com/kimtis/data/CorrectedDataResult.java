package com.kimtis.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by uiseok on 2016-09-06.
 */
public class CorrectedDataResult implements Serializable{
    @SerializedName("data")
    public NavigationData[] datas;

}
