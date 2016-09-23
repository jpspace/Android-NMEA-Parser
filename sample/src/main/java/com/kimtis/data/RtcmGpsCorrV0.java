package com.kimtis.data;

import java.io.Serializable;
import java.util.Map;

public class RtcmGpsCorrV0 implements Serializable {

	private long timestamp;
	private int gs;
	private String station;
	private double prc;
	private double rrc;
	
	public int getGs() {
		return gs;
	}

	public void setGs(int gs) {
		this.gs = gs;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
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
		return "RtcmGpsCorrV0 [gs=" + gs + ", station=" + station + ", prc=" + prc + ", rrc=" + rrc + "]";
	}
	
}
