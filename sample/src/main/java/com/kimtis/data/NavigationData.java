package com.kimtis.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NavigationData implements Serializable {

	@SerializedName("satellite_prn_number")
	private int satellite_prn_number;	// Satellite PRN Number
	@SerializedName("epoch")
	private String epoch;				// Epoch of current Satellite PRN Number
	@SerializedName("sv_clock_bias")
	private String sv_clock_bias;		// Space Vehicle clock bias (Seconds)
	@SerializedName("sv_clock_drift")
	private String sv_clock_drift;		// Space Vehicle clock drift (Seconds/Seconds)
	@SerializedName("sv_clock_drift_rate")
	private String sv_clock_drift_rate;	// Space Vehicle clock drift rate (Seconds/Seconds^2)

	@SerializedName("iode")
	private String iode;				// Issue Of Date Ephemeris
	@SerializedName("crs")
	private String crs;					// Radius Correction Sinus Component (Meters)

	@SerializedName("delta_n")
	private String delta_n;				// Delta n (Radians/Seconds)
	@SerializedName("mo")
	private String mo;					// Mo Angle (Radians)

	@SerializedName("cuc")
	private String cuc;					// Latitude Correction Cosinus Component (Radians)
	@SerializedName("eccentricity")
	private String eccentricity;		// Orbit Eccentricity
	@SerializedName("cus")
	private String cus;					// Latitude Correction Sinus Component (Radians)
	@SerializedName("sqrt_a")
	private String sqrt_a;				// Square root of the orbit semi major axis (Meters^0.5)

	@SerializedName("toe")
	private String toe;					// Time Of Ephemeris (Seconds of GPS Week)
	@SerializedName("cic")
	private String cic;					// Inclination Correction Cosinus Component (Radians)
	@SerializedName("big_omega")
	private String big_omega;			// OMEGA Angle (Radians)
	@SerializedName("cis")
	private String cis;					// Angular Velocity (Radians)

	@SerializedName("io")
	private String io;					// Initial Inclination (Radians)
	@SerializedName("crc")
	private String crc;					// Radius Correction Cosinus Component (Meters)
	@SerializedName("little_omega")
	private String little_omega;		// Omega Angle (Radians)
	@SerializedName("omega_dot")
	private String omega_dot;			// Angular Velocity (Radians/Seconds)


	@SerializedName("idot")
	private String idot;				// Inclination Rate (Radians/Seconds)
	@SerializedName("l2_code_channel")
	private String l2_code_channel;		// Codes on L2 channel
	@SerializedName("gps_week")
	private String gps_week;			// GPS Week Number (to go with TOE)
	@SerializedName("l2_p_data_flag")
	private String l2_p_data_flag;		// L2 P data flag

	@SerializedName("sv_accuraccy")
	private String sv_accuraccy;		// Space Vehicle Accuraccy (Meters)
	@SerializedName("sv_health")
	private String sv_health;			// The health of the signal components
	@SerializedName("tgd")
	private String tgd;					// Total Group Delay (Seconds)
	@SerializedName("iodc")
	private String iodc;				// Issue of Data Clock

	@SerializedName("transmission_time")
	private String transmission_time;	// Transmission Time of the Message (Seconds of GPS Week)
	@SerializedName("interval")
	private String interval;			// (see ICD-GPS-200, 20.3.3.4.3.1): (Hours)

	public com.ppsoln.domain.NavigationData getNavigationData(){
		com.ppsoln.domain.NavigationData result = new com.ppsoln.domain.NavigationData();
		result.setSatellite_prn_number(satellite_prn_number);
		result.setEpoch(epoch);
		result.setSv_clock_bias(sv_clock_bias);
		result.setSv_clock_drift(sv_clock_drift);
		result.setSv_clock_drift_rate(sv_clock_drift_rate);
		result.setIode(iode);
		result.setCrs(crs);
		result.setDelta_n(delta_n);
		result.setMo(mo);
		result.setCuc(cuc);
		result.setEccentricity(eccentricity);
		result.setCus(cus);
		result.setSqrt_a(sqrt_a);
		result.setToe(toe);
		result.setCic(cic);
		result.setBig_omega(big_omega);
		result.setCis(cis);
		result.setIo(io);
		result.setCrc(crc);
		result.setLittle_omega(little_omega);
		result.setOmega_dot(omega_dot);
		result.setIdot(idot);
		result.setL2_code_channel(l2_code_channel);
		result.setGps_week(gps_week);
		result.setL2_p_data_flag(l2_p_data_flag);
		result.setSv_accuraccy(sv_accuraccy);
		result.setSv_health(sv_health);
		result.setTgd(tgd);
		result.setIodc(iodc);
		result.setTransmission_time(transmission_time);
		result.setInterval(interval);
		return result;
	}

	public int getSatellite_prn_number() {
		return satellite_prn_number;
	}
	public void setSatellite_prn_number(int satellite_prn_number) {
		this.satellite_prn_number = satellite_prn_number;
	}
	public String getEpoch() {
		return epoch;
	}
	public void setEpoch(String epoch) {
		this.epoch = epoch;
	}
	public String getSv_clock_bias() {
		return sv_clock_bias;
	}
	public void setSv_clock_bias(String sv_clock_bias) {
		this.sv_clock_bias = sv_clock_bias;
	}
	public String getSv_clock_drift() {
		return sv_clock_drift;
	}
	public void setSv_clock_drift(String sv_clock_drift) {
		this.sv_clock_drift = sv_clock_drift;
	}
	public String getSv_clock_drift_rate() {
		return sv_clock_drift_rate;
	}
	public void setSv_clock_drift_rate(String sv_clock_drift_rate) {
		this.sv_clock_drift_rate = sv_clock_drift_rate;
	}
	public String getIode() {
		return iode;
	}
	public void setIode(String iode) {
		this.iode = iode;
	}
	public String getCrs() {
		return crs;
	}
	public void setCrs(String crs) {
		this.crs = crs;
	}
	public String getDelta_n() {
		return delta_n;
	}
	public void setDelta_n(String delta_n) {
		this.delta_n = delta_n;
	}
	public String getMo() {
		return mo;
	}
	public void setMo(String mo) {
		this.mo = mo;
	}
	public String getCuc() {
		return cuc;
	}
	public void setCuc(String cuc) {
		this.cuc = cuc;
	}
	public String getEccentricity() {
		return eccentricity;
	}
	public void setEccentricity(String eccentricity) {
		this.eccentricity = eccentricity;
	}
	public String getCus() {
		return cus;
	}
	public void setCus(String cus) {
		this.cus = cus;
	}
	public String getSqrt_a() {
		return sqrt_a;
	}
	public void setSqrt_a(String sqrt_a) {
		this.sqrt_a = sqrt_a;
	}
	public String getToe() {
		return toe;
	}
	public void setToe(String toe) {
		this.toe = toe;
	}
	public String getCic() {
		return cic;
	}
	public void setCic(String cic) {
		this.cic = cic;
	}
	public String getBig_omega() {
		return big_omega;
	}
	public void setBig_omega(String big_omega) {
		this.big_omega = big_omega;
	}
	public String getCis() {
		return cis;
	}
	public void setCis(String cis) {
		this.cis = cis;
	}
	public String getIo() {
		return io;
	}
	public void setIo(String io) {
		this.io = io;
	}
	public String getCrc() {
		return crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	public String getLittle_omega() {
		return little_omega;
	}
	public void setLittle_omega(String little_omega) {
		this.little_omega = little_omega;
	}
	public String getOmega_dot() {
		return omega_dot;
	}
	public void setOmega_dot(String omega_dot) {
		this.omega_dot = omega_dot;
	}
	public String getIdot() {
		return idot;
	}
	public void setIdot(String idot) {
		this.idot = idot;
	}
	public String getL2_code_channel() {
		return l2_code_channel;
	}
	public void setL2_code_channel(String l2_code_channel) {
		this.l2_code_channel = l2_code_channel;
	}
	public String getGps_week() {
		return gps_week;
	}
	public void setGps_week(String gps_week) {
		this.gps_week = gps_week;
	}
	public String getL2_p_data_flag() {
		return l2_p_data_flag;
	}
	public void setL2_p_data_flag(String l2_p_data_flag) {
		this.l2_p_data_flag = l2_p_data_flag;
	}
	public String getSv_accuraccy() {
		return sv_accuraccy;
	}
	public void setSv_accuraccy(String sv_accuraccy) {
		this.sv_accuraccy = sv_accuraccy;
	}
	public String getSv_health() {
		return sv_health;
	}
	public void setSv_health(String sv_health) {
		this.sv_health = sv_health;
	}
	public String getTgd() {
		return tgd;
	}
	public void setTgd(String tgd) {
		this.tgd = tgd;
	}
	public String getIodc() {
		return iodc;
	}
	public void setIodc(String iodc) {
		this.iodc = iodc;
	}
	public String getTransmission_time() {
		return transmission_time;
	}
	public void setTransmission_time(String transmission_time) {
		this.transmission_time = transmission_time;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	
	@Override
	public String toString() {
		return "NavigationData [satellite_prn_number=" + satellite_prn_number + ", epoch=" + epoch + ", sv_clock_bias="
				+ sv_clock_bias + ", sv_clock_drift=" + sv_clock_drift + ", sv_clock_drift_rate=" + sv_clock_drift_rate
				+ ", iode=" + iode + ", crs=" + crs + ", delta_n=" + delta_n + ", mo=" + mo + ", cuc=" + cuc
				+ ", eccentricity=" + eccentricity + ", cus=" + cus + ", sqrt_a=" + sqrt_a + ", toe=" + toe + ", cic="
				+ cic + ", big_omega=" + big_omega + ", cis=" + cis + ", io=" + io + ", crc=" + crc + ", little_omega="
				+ little_omega + ", omega_dot=" + omega_dot + ", idot=" + idot + ", l2_code_channel=" + l2_code_channel
				+ ", gps_week=" + gps_week + ", l2_p_data_flag=" + l2_p_data_flag + ", sv_accuraccy=" + sv_accuraccy
				+ ", sv_health=" + sv_health + ", tgd=" + tgd + ", iodc=" + iodc + ", transmission_time="
				+ transmission_time + ", interval=" + interval + "]";
	}
	
}
