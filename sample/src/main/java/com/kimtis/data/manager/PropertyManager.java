package com.kimtis.data.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kimtis.MyApplication;

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

    private static final String DATUM_X = "datum_x";

    public void setDatumX(float datum_x) {
        mEditor.putFloat(DATUM_X, datum_x);
        mEditor.commit();
    }

    public float getDatumX() {
        return mPrefs.getFloat(DATUM_X, 0.0f);
    }

    private static final String DATUM_Y = "datum_y";

    public void setDatumY(float datum_y) {
        mEditor.putFloat(DATUM_Y, datum_y);
        mEditor.commit();
    }

    public float getDatumY() {
        return mPrefs.getFloat(DATUM_Y, 0.0f);
    }

    private static final String DATUM_Z = "datum_z";

    public void setDatumZ(float datum_z) {
        mEditor.putFloat(DATUM_Z, datum_z);
        mEditor.commit();
    }

    public float getDatumZ() {
        return mPrefs.getFloat(DATUM_Z, 0.0f);
    }

}
