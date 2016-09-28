package com.kimtis.data;

import com.kimtis.data.manager.PropertyManager;
import com.ppsoln.commons.position.AzElAngle;
import com.ppsoln.commons.position.LatLngAlt;
import com.ppsoln.commons.position.XYZ;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import kr.jpspace.androidnmeaparser.SatelliteData;

/**
 * Created by uiseok on 2016-09-23.
 */

public class CachedData {

    private static final int MAX_LIST_SIZE = 100;

    private static class Holder {
        private static final CachedData INSTANCE = new CachedData();
    }


    private CachedData() {
        datum = new XYZ(
                Double.parseDouble(PropertyManager.getInstance().getDatumX()),
                Double.parseDouble(PropertyManager.getInstance().getDatumY()),
                Double.parseDouble(PropertyManager.getInstance().getDatumZ()));

        estmDataList = new LinkedList<EstmData>();
        satelliteDataList = new ArrayList<SatelliteData>();
        skyPlotData = new ArrayList<SkyPlotData>();
        navigationDatas = new NavigationData[33];
    }

    public static CachedData getInstance() {
        return Holder.INSTANCE;
    }

    private LatLngAlt currentPosition;

    private String ggaTimeString;
    private String rmcTimeString;
    private String prnArrayString;
    private Date rmcTime;


    private List<SkyPlotData> skyPlotData;

    private List<SatelliteData> satelliteDataList;

    private LatLngAlt modifiedPosition;

    private XYZ datum;

    private NavigationData[] navigationDatas;

    private NavigationHeader navigationHeader;

    public NavigationHeader getNavigationHeader() {
        return navigationHeader;
    }

    public com.ppsoln.domain.NavigationData[] getNavigationDatas() {
        com.ppsoln.domain.NavigationData[] result = new com.ppsoln.domain.NavigationData[navigationDatas.length];
        if (navigationDatas != null && navigationDatas.length > 0) {
            for (int i = 1; i < navigationDatas.length; i++) {
                result[i] = navigationDatas[i].getNavigationData();
            }
            return result;
        } else {
            return null;
        }
    }

    public void setNavigationHeader(NavigationHeader navigationHeader) {
        this.navigationHeader = navigationHeader;
    }

    public void setNavigationDatas(NavigationData[] datas) {
        for (int i = 0; i < datas.length; i++) {
            navigationDatas[i + 1] = datas[i];
        }
    }


    public void addSatelliteData(SatelliteData data) {
        this.satelliteDataList.add(data);
    }

    public void addSatelliteDatas(List<SatelliteData> datas) {
        this.satelliteDataList.addAll(datas);
    }

    public List<SatelliteData> getSatelliteDataList() {
        if (satelliteDataList.isEmpty()) {
            return null;
        } else {
            return satelliteDataList;
        }
    }

    public void removeAllSatelliteDataList(){
        satelliteDataList.clear();
    }


    public void addSkyPlotData(AzElAngle data, int prn, boolean havePRC) {
        this.skyPlotData.add(new SkyPlotData(prn, data, havePRC));
    }

    public void removeSkyPlotList() {
        this.skyPlotData.clear();
    }

    public List<SkyPlotData> getSkyPlotData() {
        return skyPlotData;
    }


    public XYZ getDatum() {
        return datum;
    }

    public void setDatum(XYZ datum) {
        this.datum = datum;
    }

    private List<EstmData> estmDataList;

    public void addEstmData(EstmData data) {
        if (estmDataList.size() > MAX_LIST_SIZE) {
            estmDataList.remove(0);
        }
        estmDataList.add(data);
    }

    public List<EstmData> getEstmDataList() {
        return estmDataList;
    }

//    public EstmData getLatestEstmData() {
//        try {
//            return (EstmData) ((LinkedList) estmDataList).getLast();
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public EstmData getEstmData(String time) {
        for (int i = 0; i < estmDataList.size(); i++) {
            if (estmDataList.get(i).getTime().equals(time))
                return estmDataList.get(i);
        }
        return null;
    }

    public int getEstmDataListSize() {
        return estmDataList.size();
    }


    public LatLngAlt getModifiedPosition() {
        return modifiedPosition;
    }

    public void setModifiedPosition(LatLngAlt modifiedPosition) {
        this.modifiedPosition = modifiedPosition;
    }

    public String gpgga, gpgsa;

    public String getGpgga() {
        return gpgga;
    }

    public void setGpgga(String gpgga) {
        this.gpgga = gpgga;
    }

    public String getGpgsa() {
        return gpgsa;
    }

    public void setGpgsa(String gpgsa) {
        this.gpgsa = gpgsa;
    }

    public Date getRmcTime() {
        return rmcTime;
    }

    public void setRmcTime(Date rmcTime) {
        this.rmcTime = rmcTime;
    }

    public String getPrnArrayString() {
        return prnArrayString;
    }

    public void setPrnArrayString(String prnArrayString) {
        this.prnArrayString = prnArrayString;
    }

    public String getRmcTimeString() {
        return rmcTimeString;
    }

    public void setRmcTimeString(String rmcTimeString) {
        this.rmcTimeString = rmcTimeString;
    }

    public void setCurrentPosition(LatLngAlt currentPosition) {
        this.currentPosition = currentPosition;
    }

    public LatLngAlt getCurrentPosition() {
        return this.currentPosition;
    }


    public String getGgaTimeString() {
        return ggaTimeString;
    }

    public void setGgaTimeString(String ggaTimeString) {
        this.ggaTimeString = ggaTimeString;
    }
}
