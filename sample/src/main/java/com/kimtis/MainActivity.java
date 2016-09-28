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
import com.kimtis.data.CorrectedDataResult;
import com.kimtis.data.EstmData;
import com.kimtis.data.NavigationHeader;
import com.kimtis.data.PRCDataResult;
import com.kimtis.data.constant.BasicConstant;
import com.kimtis.data.manager.NetworkManager;
import com.kimtis.data.manager.PropertyManager;
import com.kimtis.graph.GraphActivity;
import com.kimtis.navigation.NavigationActivity;
import com.kimtis.settings.SettingsActivity;
import com.kimtis.skyplot.SkyPlotActivity;
import com.ppsoln.commons.position.AzElAngle;
import com.ppsoln.commons.position.LatLngAlt;
import com.ppsoln.commons.position.XYZ;
import com.ppsoln.commons.satellite.GpsSatellitePosition;
import com.ppsoln.commons.solution.GlobalPressTemp;
import com.ppsoln.commons.solution.Klobuchar;
import com.ppsoln.commons.utility.ConvertFactory;
import com.ppsoln.commons.utility.MessageParser;
import com.ppsoln.commons.utility.TransformationFactory;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kr.jpspace.androidnmeaparser.Nmea;
import kr.jpspace.androidnmeaparser.SatelliteData;
import kr.jpspace.androidnmeaparser.gps.GGA;
import kr.jpspace.androidnmeaparser.gps.GSA;
import kr.jpspace.androidnmeaparser.gps.GSV;
import kr.jpspace.androidnmeaparser.gps.RMC;

public class MainActivity extends AppCompatActivity {


    String oldNmeaTag = null;

    Handler mHandler, handler;
    boolean isBackPressed = false;
    LocationManager mLM;
    GpsStatus.NmeaListener nmeaListener;


    Button btn_navi, btn_graph, btn_skyplot, btn_settings;
    boolean isOnGGA = false, isOnGSA = false, isOnRMC = false;

    int[] snr_array;

    LocationListener locationListener;
    boolean isOnLocationReceived = false;
    EstmData estmData;
    LatLngAlt lla;
    boolean isFirst = PropertyManager.getInstance().getIsFirstStart();
    Date new_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
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

                if (isFirst) {
                    Log.e("location received", "location receiving");
                    Toast.makeText(MainActivity.this, "nmea receiving..", Toast.LENGTH_SHORT).show();
                    PropertyManager.getInstance().setDatumX(TransformationFactory.toXyz(
                            new LatLngAlt(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    location.getAltitude())).getX() + "");
                    PropertyManager.getInstance().setDatumY(TransformationFactory.toXyz(
                            new LatLngAlt(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    location.getAltitude())).getY() + "");
                    PropertyManager.getInstance().setDatumZ(TransformationFactory.toXyz(
                            new LatLngAlt(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    location.getAltitude())).getZ() + "");
                    PropertyManager.getInstance().setIsFirstStart(false);
                    isFirst = false;
                    Log.e("datum", PropertyManager.getInstance().getDatumX() + ", "
                            + PropertyManager.getInstance().getDatumY() + ", "
                            + PropertyManager.getInstance().getDatumZ());
                    CachedData.getInstance().setDatum(TransformationFactory.toXyz(new LatLngAlt(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    location.getAltitude())));
                    CachedData.getInstance().setCurrentPosition(new LatLngAlt(
                            location.getLatitude(),
                            location.getLongitude(),
                            location.getAltitude()));
                }
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


                String tagString = nmea.substring(1, 6);
                if (tagString.equals(oldNmeaTag)) return;
                oldNmeaTag = tagString;
                if (tagString.equals("GPGGA")) {

                    // lat, lon, altitude + sea~ -> 전송
                    GGA tempGGA = Nmea.getInstnace().getGGAData(nmea);

                    if (!isOnGGA) {
                        try {

                            lla = new LatLngAlt();
                            lla.setLatitude(tempGGA.getLatitude());
                            lla.setLongitude(tempGGA.getLongitude());
                            lla.setAltitude(tempGGA.getAltitude());
                            isOnGGA = true;

                            CachedData.getInstance().setCurrentPosition(lla);
                            CachedData.getInstance().setGgaTimeString(tempGGA.getUtcString());
                            CachedData.getInstance().setGpgga(nmea);

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
                                CachedData.getInstance().setPrnArrayString(Arrays.toString(tempGSA.getSatelliteListUsedInFix().toArray()));
                            isOnGSA = true;
                            CachedData.getInstance().setGpgsa(nmea);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } else if (tagString.equals("GPGSV")) {

                    // PRN, SNR 추출-> 전송x 표시만(SkyPlotView)
                    try {
                        GSV gsv = Nmea.getInstnace().getGSVData(nmea);
                        if (gsv.getTotalNumberOfMessages().equals(gsv.getMessageNumber()))
                            CachedData.getInstance().removeAllSatelliteDataList();

                        List<SatelliteData> prn_list = gsv.getSatelliteListData();
                        CachedData.getInstance().addSatelliteDatas(prn_list);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (tagString.equals("GPRMC")) {

                    // Time (UTC) -> 전송
                    RMC rmc = Nmea.getInstnace().getRMCData(nmea);
                    if (!isOnRMC) {
                        try {
                            String time = rmc.getTimeOfFixUTC();
                            String date = rmc.getDateOfFix();

                            if (time != null && date != null) {
                                new_date = new Date(
                                        Integer.parseInt(date.substring(4, date.length())) + 100,
                                        Integer.parseInt(date.substring(2, 4)) - 1,
                                        Integer.parseInt(date.substring(0, 2)),
                                        (int) Double.parseDouble(time.substring(0, 2)),
                                        (int) Double.parseDouble(time.substring(2, 4)),
                                        (int) Double.parseDouble(time.substring(4, time.length()))
                                );
                                isOnRMC = true;
                                CachedData.getInstance().setRmcTimeString(time.toString());
                                CachedData.getInstance().setRmcTime(new_date);
                                Log.e("rmc time string", time.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    return;
                }

                if (CachedData.getInstance().getRmcTimeString() != null &&
                        CachedData.getInstance().getRmcTimeString().equals(CachedData.getInstance().getGgaTimeString())
                        && isOnGGA && isOnGSA && isOnRMC) {
                    // 오차 계산용 객체 별도 생성

                    NetworkManager.getInstnace().getPRCData(MainActivity.this
                            , lla.getLatitude() + ""
                            , lla.getLongitude() + ""
                            , CachedData.getInstance().getPrnArrayString()
                            , new_date
                            , new NetworkManager.OnResultListener<PRCDataResult>() {
                                @Override
                                public void onSuccess(PRCDataResult result) {

                                    try {
                                        addSkyPlotDatas(CachedData.getInstance().getNavigationDatas(),
                                                result.getRtcmResult(),
                                                new_date);
                                        XYZ xyz = nmeaCp(
                                                CachedData.getInstance().getNavigationHeader().toNavigationHeader(),
                                                CachedData.getInstance().getNavigationDatas(),
                                                result.getRtcmResult(),
                                                lla,
                                                new_date,
                                                4);
                                        estmData = new EstmData();
                                        estmData.setBefore_latLngAlt(lla);
                                        LatLngAlt modified_lla = TransformationFactory.toLatLngAlt(xyz);
                                        estmData.setModified_latLngAlt(modified_lla);
                                        estmData.setTime(CachedData.getInstance().getRmcTime());
                                        CachedData.getInstance().addEstmData(estmData);
                                        notifyNmeaCPCalculated();
                                        Log.e("nmeaCp complete", "nmeaCp complete");
                                    } catch (Exception e) {
                                        Log.e("nmeaCP Error", e.getMessage());
                                    }
                                }

                                @Override
                                public void onFail(int code) {
                                    Log.e("Get PRC File Error", "code : " + code);
                                }
                            });

                    // 보정 후

                    isOnGGA = false;
                    isOnGSA = false;
                    isOnRMC = false;


                }

            }
        };

        mLM.addNmeaListener(nmeaListener);


        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                NetworkManager.getInstnace().getNavigationHeader(MainActivity.this,
                                        new NetworkManager.OnResultListener<NavigationHeader>() {
                                            @Override
                                            public void onSuccess(NavigationHeader result) {
                                                CachedData.getInstance().setNavigationHeader(result);
                                            }

                                            @Override
                                            public void onFail(int code) {
                                                Log.e("Get Header Error", "code : " + code);
                                            }
                                        });
                                NetworkManager.getInstnace().getCorrectData(MainActivity.this,
                                        new NetworkManager.OnResultListener<CorrectedDataResult>() {
                                            @Override
                                            public void onSuccess(CorrectedDataResult result) {
                                                CachedData.getInstance().setNavigationDatas(result.datas);
                                            }

                                            @Override
                                            public void onFail(int code) {
                                                Log.e("Get Navi Error", "code : " + code);
                                            }
                                        });

                            }
                        });

                        TimeUnit.MINUTES.sleep(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


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


    public interface OnSkyPlotAddedListener {
        public void onSkyPlotAdded();
    }

    static List<OnSkyPlotAddedListener> skyPlotListenerList = new ArrayList<OnSkyPlotAddedListener>();

    public static void registerOnSkyPlotAddedListener(
            OnSkyPlotAddedListener listener) {
        if (!skyPlotListenerList.contains(listener)) {
            skyPlotListenerList.add(listener);
        }
    }

    public static void unregisterOnSkyPlotAddedListener(
            OnSkyPlotAddedListener listener) {
        skyPlotListenerList.remove(listener);
    }

    public void notifySkyPlotAdded() {
        mHandler.removeCallbacks(sNotifyRunnable);
        mHandler.post(sNotifyRunnable);
    }

    Runnable sNotifyRunnable = new Runnable() {

        @Override
        public void run() {
            for (OnSkyPlotAddedListener listener : skyPlotListenerList) {
                listener.onSkyPlotAdded();
            }
        }
    };

    public void addSkyPlotDatas(com.ppsoln.domain.NavigationData[] datas,      // 32개 navigation data
                                com.ppsoln.domain.RtcmGpsCorrV0[] prcs, Date now) {
        // Custom Action
        Log.e("Add SkyPlot Data", "SkyPlot Data Added..");

        String[] prn_array = CachedData.getInstance().getPrnArrayString().replace("[", "").replace("]", "").split(",");

        CachedData.getInstance().removeSkyPlotList();
        for (int i = 0; i < prn_array.length; i++) {
            // 현재 스마트폰에서 보이는 위성의 위치를 계산하고 SkyPlot을 그리기 위한 위성의 정보를 저장
            if (datas != null) {
                try {
                    GpsSatellitePosition g = new GpsSatellitePosition();
                    int prn = (int) MessageParser.parseDouble(prn_array[i]);

                    g.setEph(datas[prn]);
                    XYZ xyz = g.getSatellitePosition(MessageParser.parseDouble(ConvertFactory.toGs(now) + ""));
                    AzElAngle aea = TransformationFactory.toAzEl(xyz, CachedData.getInstance().getDatum());
                    CachedData.getInstance().addSkyPlotData(aea, prn, prcs[i].getPrn() != 0);


                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                }
            }
        }
        notifySkyPlotAdded();
    }

    public static XYZ nmeaCp(
            com.ppsoln.domain.NavigationHeader header,   // 1개 navigation header
            com.ppsoln.domain.NavigationData[] datas,      // 32개 navigation data
            com.ppsoln.domain.RtcmGpsCorrV0[] prcs,      // 사용된 위성 Prc정보들 ()
            LatLngAlt position,       // 보정전 위치 (NMEA)
            Date now,               // 시간 (NMEA)
            int interrupt            // prc 이정도 없으면 봐주겠다
    ) {

        Log.e("nmeaCP Called", "nmeaCp Called");
        /** Calculation Start **/
//        // TODO : Erase
//        return TransformationFactory.toXyz(position);
        // 임계 고도각
        double eleCut = 15;
        int __interrupt = 0;

        // 초기화
        GlobalPressTemp trop = new GlobalPressTemp();
        Klobuchar iono = new Klobuchar()
                .setIonAlpha(header.getIon_alpha())
                .setIonBeta(header.getIon_beta());

        // 행렬 계산
        RealMatrix HTH = new Array2DRowRealMatrix(4, 4);
        RealMatrix HTy = new Array2DRowRealMatrix(4, 1);
        RealMatrix HTprc = new Array2DRowRealMatrix(4, 1);

        // 초기 x값
        XYZ before = TransformationFactory.toXyz(position);
        double[][] xvo = {{before.getX()}, {before.getY()}, {before.getZ()}, {1}};
        RealMatrix x = new Array2DRowRealMatrix(xvo);
        RealMatrix xprc = null;

        // GPS Time 계산
        double gw = ConvertFactory.toGw(now);
        double gs = ConvertFactory.toGs(now);

        // 초기 수긴기 위치
        XYZ receiverPosition = TransformationFactory.toXyz(position);

        for (int iter = 0; iter < 1; iter++) { // ???????????????????????????

            // 행렬 초기화
            HTH.scalarMultiply(0);
            HTy.scalarMultiply(0);
            HTprc.scalarMultiply(0);

            trop.setZHD_Gw_Gs_vecSite((int) gw, (int) gs, receiverPosition);

            // 위성 갯수 만큼 루프
            for (int i = 0; i < prcs.length; i++) {

                // 보정정보가 없을 때
                if (prcs[i].getStation() == null) continue;
                __interrupt++;

                // 기본 정보
                int prn = prcs[i].getPrn();
                double prc = prcs[i].getPrc();

                // 위성 위치 계산
                GpsSatellitePosition gpsPosition =
                        new GpsSatellitePosition().setEph(datas[prn]);
                double STT = gpsPosition.getSignalTransmissionTime(gs, receiverPosition);
                double tc = gs - STT;
                XYZ satellitePosition = gpsPosition.getSatellitePosition(tc);
                satellitePosition = gpsPosition.getRotateSatellitePosition(satellitePosition, STT);

                // 방향 벡터 계산
                double[] rhoVectorVo = {
                        satellitePosition.getX() - receiverPosition.getX(),
                        satellitePosition.getY() - receiverPosition.getY(),
                        satellitePosition.getZ() - receiverPosition.getZ()};
                RealVector rhoVector = new ArrayRealVector(rhoVectorVo);

                // 거리 계산
                double rho = rhoVector.getNorm();

                // 방위각, 고도각
                AzElAngle azElAngle = TransformationFactory.toAzEl(new XYZ(
                        rhoVectorVo[0], rhoVectorVo[1], rhoVectorVo[2]), receiverPosition);

                // 임계 고도각 필터
                if (azElAngle.getDegreeElevation() >= eleCut) {

                    // 이온층 오차
                    double dIono = iono.setAzimuth(azElAngle.getDegreeAzimuth())
                            .setElevation(azElAngle.getDegreeElevation())
                            .setRreceiverPosition(receiverPosition)
                            .setGs((int) gs)
                            .getDelay();

                    // 대류권 오차
                    double dTrop = trop.setElevation(azElAngle.getDegreeElevation())
                            .getDelay();

                    double y = dIono + dTrop;
                    double cp = -dIono - dTrop - prc;

                    // H Matrix
                    double[][] hvo = {{
                            -rhoVector.getEntry(0) / rho,
                            -rhoVector.getEntry(1) / rho,
                            -rhoVector.getEntry(2) / rho, 1}};
                    RealMatrix H = new Array2DRowRealMatrix(hvo);

                    HTH = HTH.add(H.transpose().multiply(H));
                    HTy = HTy.add(H.transpose().scalarMultiply(y));
                    HTprc = HTprc.add(H.transpose().scalarMultiply(cp));

                }

            }

            Log.e("Used in fix : ", HTH.getEntry(3, 3) + "");
            if (interrupt > __interrupt) return null;

            // inv(HTH) * (HTy - HT_prc)
            xprc = x.add(new LUDecomposition(HTH).getSolver().getInverse().multiply(HTy.add(HTprc.scalarMultiply(-1))));

        }
        return new XYZ(xprc.getEntry(0, 0), xprc.getEntry(1, 0), xprc.getEntry(2, 0));

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

