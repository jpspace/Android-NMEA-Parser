package com.kimtis.data;

import com.kimtis.data.manager.PropertyManager;
import com.ppsoln.commons.position.AzElAngle;
import com.ppsoln.commons.position.LatLngAlt;
import com.ppsoln.commons.position.XYZ;
import com.ppsoln.commons.utility.TransformationFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.jpspace.androidnmeaparser.SatelliteData;

import static org.apache.commons.math3.geometry.euclidean.threed.RotationOrder.XYZ;

/**
 * Created by uiseok on 2016-09-23.
 */

public class CachedData {

    private static class Holder {
        private static final CachedData INSTANCE = new CachedData();
    }


    private CachedData() {
        datum = new LatLngAlt();
        datum = TransformationFactory.toLatLngAlt(
                new XYZ(PropertyManager.getInstance().getDatumX(),
                        PropertyManager.getInstance().getDatumY(),
                        PropertyManager.getInstance().getDatumZ()));
        estmDataList = new ArrayList<EstmData>();
        satelliteDataList = new ArrayList<SatelliteData>();
        skyPlotData = new ArrayList<SkyPlotData>();
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

    private LatLngAlt datum;



    public void addSatelliteData(SatelliteData data){
        this.satelliteDataList.add(data);
    }
    public void addSatelliteDatas(List<SatelliteData> datas){
        this.satelliteDataList.addAll(datas);
    }
    public List<SatelliteData> getSatelliteDataList(){
        return satelliteDataList;
    }

    public void addSkyPlotData(AzElAngle data, int prn){
        this.skyPlotData.add(new SkyPlotData(prn, data));
    }
    public List<SkyPlotData> getSkyPlotData(){ return skyPlotData; }


    public LatLngAlt getDatum() {
        return datum;
    }

    public void setDatum(LatLngAlt datum) {
        this.datum = datum;
    }

    private List<EstmData> estmDataList;

    public void addEstmData(EstmData data){
        estmDataList.add(data);
    }
    public List<EstmData> getEstmDataList(){
        return estmDataList;
    }
    public EstmData getEstmData(int position){
        return estmDataList.get(position);
    }
    public EstmData getEstmData(String time){
        for(int i=0; i<estmDataList.size();i++){
            if(estmDataList.get(i).getTime().equals(time))
                return estmDataList.get(i);
        }
        return null;
    }



    public LatLngAlt getModifiedPosition() {
        return modifiedPosition;
    }

    public void setModifiedPosition(LatLngAlt modifiedPosition) {
        this.modifiedPosition = modifiedPosition;
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
