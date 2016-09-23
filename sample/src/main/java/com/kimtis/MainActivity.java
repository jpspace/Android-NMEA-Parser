package com.kimtis;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kimtis.data.constant.BasicConstant;
import com.kimtis.graph.GraphActivity;
import com.kimtis.navigation.NavigationActivity;
import com.kimtis.skyplot.SkyPlotActivity;

public class MainActivity extends AppCompatActivity {


    boolean isStarted = false;
    String holeNmea = "";

    Handler mHandler;
    boolean isBackPressed = false;
    LocationManager mLM;
    GpsStatus.NmeaListener nmeaListener;
//    ViewPager viewpager;
//    MainActivityFragmentAdapter mAdapter;
//    TabHost tabHost;

    Button btn_navi, btn_graph, btn_skyplot, btn_settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        tabHost = (TabHost)findViewById(R.id.tabhost);
//        tabHost.setup();
//        viewpager = (ViewPager)findViewById(R.id.viewpager);
//        mAdapter = new MainActivityFragmentAdapter(this, getSupportFragmentManager(),tabHost,viewpager);
//        mAdapter.addTab(tabHost.newTabSpec("mainTab").setIndicator("길안내"), MainFragment.class, null );
//        mAdapter.addTab(tabHost.newTabSpec("skyplotTab").setIndicator("SkyPlot"), SkyPlotFragment.class, null );
//        TextView textview = (TextView)  tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
//        textview.setTextSize(12);
//        mAdapter.addTab(tabHost.newTabSpec("graphTab").setIndicator("통계"), GraphFragment.class, null );
//        mAdapter.addTab(tabHost.newTabSpec("settingsTab").setIndicator("설정"), SettingsFragment.class, null );

        btn_navi = (Button) findViewById(R.id.btn_navigation);
        btn_graph = (Button) findViewById(R.id.btn_graph);
        btn_skyplot = (Button) findViewById(R.id.btn_skyplot);
        btn_settings = (Button) findViewById(R.id.btn_settings);

        btn_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NavigationActivity.class));
            }
        });
        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GraphActivity.class));
            }
        });
        btn_skyplot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SkyPlotActivity.class));
            }
        });
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Comming Soon..", Toast.LENGTH_SHORT).show();
            }
        });


        mHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case BasicConstant.TimeOutConstant.MESSAGE_BACK_PRESSED_TIMEOUT:
                        isBackPressed = false;
                        break;
                }
            }
        };


        mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!mLM.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            MainActivity.this.finish();
        }


//        nmeaListener = new GpsStatus.NmeaListener() {
//            @Override
//            public void onNmeaReceived(long timestamp, String nmea) {
//
//
//
//                switch (nmea.substring(1, 6)) {
//                    case "GPGGA":
//                        GGA tempGGA = Nmea.getInstnace().getGGAData(nmea);
//
//                        break;
//                    case "GPGSA":
//                        GSA tempGSA = Nmea.getInstnace().getGSAData(nmea);
//
//                        break;
//                }
//            }
//        };


//        mLM.addNmeaListener(nmeaListener);

        Integer[] prn = new Integer[2];
        prn[0] = 1;
        prn[1] = 2;



//        NetworkManager.getInstnace().getPRCData(MainActivity.this, "0", "0", prn, new NetworkManager.OnResultListener<PRCDataResult>() {
//            @Override
//            public void onSuccess(PRCDataResult result) {
//
//            }
//
//            @Override
//            public void onFail(int code) {
//
//            }
//        });

//        NetworkManager.getInstnace().getNavigationHeader(MainActivity.this, new NetworkManager.OnResultListener<NavigationHeader>() {
//            @Override
//            public void onSuccess(NavigationHeader result) {
//                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFail(int code) {
//                Toast.makeText(MainActivity.this, code
//                        +"", Toast.LENGTH_LONG).show();
//            }
//        });

    }



    @Override
    public void onBackPressed() {

        if (!isBackPressed) {
            Toast.makeText(MainActivity.this, "한번 더 누르면 앱이 종료됩니다.",
                    Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(
                    BasicConstant.TimeOutConstant.MESSAGE_BACK_PRESSED_TIMEOUT,
                    BasicConstant.TimeOutConstant.TIMEOUT_BACK_PRESSED);
            isBackPressed = true;
        } else {
            mHandler.removeMessages(BasicConstant.TimeOutConstant.MESSAGE_BACK_PRESSED_TIMEOUT);
            super.onBackPressed();
//            mLM.removeNmeaListener(nmeaListener);
        }
    }


}

