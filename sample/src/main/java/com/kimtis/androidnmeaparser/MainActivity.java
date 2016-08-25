package com.kimtis.androidnmeaparser;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kr.jpspace.androidnmeaparser.Nmea;

public class MainActivity extends AppCompatActivity {

    TextView text_nmea;
    Button btn_start, btn_stop;

    boolean isStarted = false;
    String holeNmea="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_nmea = (TextView) findViewById(R.id.text_nmea);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_start = (Button) findViewById(R.id.btn_start);
        final LocationManager mLM;

        mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (!mLM.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("GPS not Found");  // GPS not found
                builder.setMessage("Do you want to enable?"); // Want to enable?
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
                return;
            }

        }
        mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                Toast.makeText(MainActivity.this, location.getLatitude()+" "+location.getLongitude(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });



        final GpsStatus.NmeaListener nmeaListener = new GpsStatus.NmeaListener() {
            @Override
            public void onNmeaReceived(long timestamp, String nmea) {
                if(!isStarted) {    // first start
                    if (nmea.substring(1, 6).equalsIgnoreCase("GPGGA")) {   // start with gpgga
                        isStarted = true;
                        holeNmea = "";  // initialize
                        holeNmea += nmea;
                    }
                    // before start data
                }else{  // if gpgga started
                    if(nmea.substring(1, 6).equalsIgnoreCase("GPRMC")){ // end with gprmc
                        isStarted = false;
                        holeNmea += nmea;
                        // Use Hole Nmea Data here
                        Nmea nm = new Nmea(holeNmea);
                        text_nmea.append(nm.getKeys()+"\n");
                    }else{  // until the end, holeNmea data will increase
                        holeNmea += nmea;
                    }
                }
            }
        };


        mLM.addNmeaListener(nmeaListener);

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLM.removeNmeaListener(nmeaListener);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                }
                mLM.addNmeaListener(nmeaListener);
            }
        });
    }

}

