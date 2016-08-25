package kr.jpspace.androidnmeaparser;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * Created by kimti on 2016-08-25.
 */
public class Nmea {
    public void showToast(Context context, Resources resources) {
        Toast.makeText(context, resources.getString(R.string.app_name), Toast.LENGTH_LONG).show();
    }
}
