package com.kimtis.data.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kimtis.MyApplication;
import com.kimtis.data.CachedData;
import com.ppsoln.commons.position.XYZ;

/**
 * Created by uiseok on 2016-09-25.
 */

public class PropertyManager {


    private static PropertyManager instance;

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication
                .getContext());
        mEditor = mPrefs.edit();
    }

    private static final String IS_FIRST_START = "isFirstStart";

    public void setIsFirstStart(boolean isFirstStart){
        mEditor.putBoolean(IS_FIRST_START,isFirstStart);
        mEditor.commit();
    }
    public boolean getIsFirstStart(){return mPrefs.getBoolean("isFirstStart",true);}

    private static final String DATUM_X = "datum_x";

    public void setDatumX(String datum_x) {
        mEditor.putString(DATUM_X, datum_x);
        mEditor.commit();
    }

    public String getDatumX() {
        return mPrefs.getString(DATUM_X, "0.0");
    }

    private static final String DATUM_Y = "datum_y";

    public void setDatumY(String datum_y) {
        mEditor.putString(DATUM_Y, datum_y);
        mEditor.commit();
    }

    public String getDatumY() {
        return mPrefs.getString(DATUM_Y, "0.0");
    }

    private static final String DATUM_Z = "datum_z";

    public void setDatumZ(String datum_z) {
        mEditor.putString(DATUM_Z, datum_z);
        mEditor.commit();
    }

    public String getDatumZ() {
        return mPrefs.getString(DATUM_Z,"0.0");
    }

    public void updateDatum(){
        CachedData.getInstance().setDatum(new XYZ(Double.parseDouble(getDatumX()),Double.parseDouble(getDatumY()),Double.parseDouble(getDatumZ())));
    }
}
