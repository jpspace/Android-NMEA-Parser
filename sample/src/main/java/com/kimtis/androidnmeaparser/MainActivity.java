package com.kimtis.androidnmeaparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.jpspace.androidnmeaparser.Nmea;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nmea nmea = new Nmea();
        nmea.showToast(this, getResources());
    }
}
