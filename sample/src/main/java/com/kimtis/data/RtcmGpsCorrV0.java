package com.kimtis.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RtcmGpsCorrV0 implements Serializable {

	@SerializedName("date")
	private String date;
	@SerializedName("station")
	private String station;
	@SerializedName("prn")
	private int prn;
	@SerializedName("prc")
	private double prc;
	@SerializedName("rrc")
	private double rrc;

	public com.ppsoln.domain.RtcmGpsCorrV0 getRtcmGpsCorrV0(){
		com.ppsoln.domain.RtcmGpsCorrV0 result = new com.ppsoln.domain.RtcmGpsCorrV0();
		result.setDate(date);
		result.setPrc(prc);
		result.setPrn(prn);
		result.setRrc(rrc);
		result.setStation(station);
		return result;
	}
	

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public int getPrn() {
		return prn;
	}
	public void setPrn(int prn) {
		this.prn = prn;
	}
	public double getPrc() {
		return prc;
	}
	public void setPrc(double prc) {
		this.prc = prc;
	}
	public double getRrc() {
		return rrc;
	}
	public void setRrc(double rrc) {
		this.rrc = rrc;
	}

	@Override
	public String toString() {
		return "RtcmGpsCorrV0 [date=" + date + ", station=" + station + ", prn=" + prn + ", prc=" + prc + ", rrc=" + rrc
				+ "]";
	}
	
}
