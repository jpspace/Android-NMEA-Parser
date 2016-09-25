package com.kimtis;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kimtis.data.CachedData;
import com.kimtis.data.EstmData;
import com.kimtis.data.constant.BasicConstant;
import com.kimtis.graph.GraphActivity;
import com.kimtis.navigation.NavigationActivity;
import com.kimtis.skyplot.SkyPlotActivity;
import com.ppsoln.commons.position.AzElAngle;
import com.ppsoln.commons.position.LatLngAlt;
import com.ppsoln.commons.position.XYZ;
import com.ppsoln.commons.satellite.GpsSatellitePosition;
import com.ppsoln.commons.utility.ConvertFactory;
import com.ppsoln.commons.utility.MessageParser;
import com.ppsoln.commons.utility.TransformationFactory;
import com.ppsoln.domain.NavigationData;
import com.ppsoln.domain.NavigationHeader;
import com.ppsoln.domain.RtcmGpsCorrV0;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.jpspace.androidnmeaparser.Nmea;
import kr.jpspace.androidnmeaparser.SatelliteData;
import kr.jpspace.androidnmeaparser.gps.GGA;
import kr.jpspace.androidnmeaparser.gps.GSA;
import kr.jpspace.androidnmeaparser.gps.GSV;
import kr.jpspace.androidnmeaparser.gps.RMC;

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
    boolean isOnGGA = false, isOnGSA = false, isOnRMC = false;

    int[] snr_array;

    LocationListener locationListener;


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

        snr_array = new int[33];

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

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        nmeaListener = new GpsStatus.NmeaListener() {
            @Override
            public void onNmeaReceived(long timestamp, String nmea) {
                Log.e("Full nmea", nmea);
                LatLngAlt lla = null;
                String tagString = nmea.substring(1, 6);
                if (tagString.equals("GPGGA")) {

                    // lat, lon, altitude + sea~ -> 전송
                    GGA tempGGA = Nmea.getInstnace().getGGAData(nmea);
                    if (!isOnGGA) {
                        try {
                            lla = new LatLngAlt();
                            lla.setLatitude(tempGGA.getLatitude());
                            lla.setLongitude(tempGGA.getLongitude());
                            lla.setAltitude(tempGGA.getAltitude());
                            CachedData.getInstance().setCurrentPosition(lla);
                            CachedData.getInstance().setGgaTimeString(tempGGA.getUtcString());
                            Log.e("gga utc string", tempGGA.getUtcString());
                            isOnGGA = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (tagString.equals("GPGSA")) {

                    // PRN array -> 전송 (전송 시 정렬해서 보낼 것)
                    GSA tempGSA = Nmea.getInstnace().getGSAData(nmea);
                    if (!isOnGSA) {
                        try {
                            if (!tempGSA.getSatelliteListUsedInFix().isEmpty())
                                CachedData.getInstance().setPrnArrayString(tempGSA.getSatelliteListUsedInFix().toString());
                            isOnGSA = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else if (tagString.equals("GPGSV")) {

                    // PRN, SNR 추출-> 전송x 표시만(SkyPlotView)

                    GSV gsv = Nmea.getInstnace().getGSVData(nmea);
                    List<SatelliteData> prn_list = gsv.getSatelliteListData();
                    CachedData.getInstance().addSatelliteDatas(prn_list);


                } else if (tagString.equals("GPRMC")) {

                    // Time (UTC) -> 전송
                    RMC rmc = Nmea.getInstnace().getRMCData(nmea);
                    if (!isOnRMC) {
                        try {
                            String time = rmc.getTimeOfFixUTC();
                            String date = rmc.getDateOfFix();

                            if (time != null && date != null) {
                                Date new_date = new Date(
                                        Integer.parseInt(date.substring(4, date.length())) + 100,
                                        Integer.parseInt(date.substring(2, 4)) - 1,
                                        Integer.parseInt(date.substring(0, 2)),
                                        (int) Double.parseDouble(time.substring(0, 2)),
                                        (int) Double.parseDouble(time.substring(2, 4)),
                                        (int) Double.parseDouble(time.substring(4, time.length()))
                                );
                                CachedData.getInstance().setRmcTimeString(time.toString());
                                Log.e("rmc time string", time.toString());
                            }


                            isOnRMC = true;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

                if (CachedData.getInstance().getRmcTimeString() != null &&
                        CachedData.getInstance().getRmcTimeString().equals(CachedData.getInstance().getGgaTimeString())
                        && isOnGGA && isOnGSA && isOnRMC) {
                    // 계산 메소드 호출
                    // TODO


                    EstmData estmData = new EstmData();
                    estmData.setBefore_latLngAlt(lla);

                    // 보정 전
                    // 계산
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            XYZ xyz = nmeaCP(asdf,asdf,asdf,asdf);
//                        }
//                    });
                    // 보정 후

                    // CALL NmeaCP
                    LatLngAlt modified_lla = null;
                    estmData.setModified_latLngAlt(modified_lla);
                    CachedData.getInstance().addEstmData(estmData);
                    notifyNmeaCPCalculated();
                    Log.e("ok", "okok");

                    isOnGGA = false;
                    isOnGSA = false;
                    isOnRMC = false;
                }

            }
        };


        mLM.addNmeaListener(nmeaListener);


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


    public interface OnNmeaCPCalculatedListener {
        public void onNmeaCPCalculated();
    }

    static List<OnNmeaCPCalculatedListener> nListenerList = new ArrayList<OnNmeaCPCalculatedListener>();

    public static void registerOnNmeaCPCalculatedListener(
            OnNmeaCPCalculatedListener listener) {
        if (!nListenerList.contains(listener)) {
            nListenerList.add(listener);
        }
    }

    public static void unregisterOnNmeaCPCalculatedListener(
            OnNmeaCPCalculatedListener listener) {
        nListenerList.remove(listener);
    }

    public void notifyNmeaCPCalculated() {
        mHandler.removeCallbacks(nNotifyRunnable);
        mHandler.post(nNotifyRunnable);
    }

    Runnable nNotifyRunnable = new Runnable() {

        @Override
        public void run() {
            for (OnNmeaCPCalculatedListener listener : nListenerList) {
                listener.onNmeaCPCalculated();
            }
        }
    };

    public static XYZ nmeaCp(
            NavigationHeader header,   // 1개 navigation header
            NavigationData[] datas,      // 32개 navigation data
            RtcmGpsCorrV0[] prcs,      // 사용된 위성 Prc정보들 ()
            LatLngAlt position,       // 보정전 위치 (NMEA)
            Date now,               // 시간 (NMEA)
            int maxIter               // 최대 루프 횟수 (임의지정)
    ) {

        String[] prn_array = CachedData.getInstance().getPrnArrayString().split(",");
        for (int i = 0; i < prn_array.length; i++) {
            // 현재 스마트폰에서 보이는 위성의 위치를 계산하고 SkyPlot을 그리기 위한 위성의 정보를 저장
            GpsSatellitePosition g = new GpsSatellitePosition();
            int prn = (int) MessageParser.parseDouble(prn_array[i]);
            g.setEph(datas[prn]);
            XYZ xyz = g.getSatellitePosition(ConvertFactory.toGs(now));
            AzElAngle aea = TransformationFactory.toAzEl(xyz, CachedData.getInstance().getDatum());
            CachedData.getInstance().addSkyPlotData(aea, prn);
        }

        return TransformationFactory.toXyz(position);
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
            mLM.removeUpdates(locationListener);
            mLM.removeNmeaListener(nmeaListener);
            super.onBackPressed();
        }
    }


}

