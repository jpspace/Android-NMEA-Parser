package com.kimtis.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class NavigationHeader implements Serializable {


	@SerializedName("version")
	private String version;				// Rinex Format Version
	@SerializedName("ion_alpha")
	private double[] ion_alpha;			// Ion Alpha (A0~A3)
	@SerializedName("ion_beta")
	private double[] ion_beta;			// Ion Beta (B0~B3)
	@SerializedName("a0_polynomial_term")
	private double a0_polynomial_term;	// Almanac parameter to compute time in UTC : A0
	@SerializedName("a1_polynomial_term")
	private double a1_polynomial_term;	// Almanac parameter to compute time in UTC : A1
	@SerializedName("reference_time")
	private int reference_time;			// Reference time for UTC data
	@SerializedName("week_number")
	private int week_number;			// UTC reference week number
	@SerializedName("leap_seconds")
	private int leap_seconds;			// Number of leap seconds

	public com.ppsoln.domain.NavigationHeader toNavigationHeader(){
		com.ppsoln.domain.NavigationHeader header = new com.ppsoln.domain.NavigationHeader();
		header.setA0_polynomial_term(this.a0_polynomial_term);
		header.setA1_polynomial_term(this.a1_polynomial_term);
		header.setIon_alpha(this.ion_alpha);
		header.setIon_beta(this.ion_beta);
		header.setLeap_seconds(this.leap_seconds);
		header.setReference_time(this.reference_time);
		header.setVersion(this.version);
		header.setWeek_number(this.week_number);
		return header;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public double[] getIon_alpha() {
		return ion_alpha;
	}
	public void setIon_alpha(double[] ion_alpha) {
		this.ion_alpha = ion_alpha;
	}
	public double[] getIon_beta() {
		return ion_beta;
	}
	public void setIon_beta(double[] ion_beta) {
		this.ion_beta = ion_beta;
	}
	public double getA0_polynomial_term() {
		return a0_polynomial_term;
	}
	public void setA0_polynomial_term(double a0_polynomial_term) {
		this.a0_polynomial_term = a0_polynomial_term;
	}
	public double getA1_polynomial_term() {
		return a1_polynomial_term;
	}
	public void setA1_polynomial_term(double a1_polynomial_term) {
		this.a1_polynomial_term = a1_polynomial_term;
	}
	public int getReference_time() {
		return reference_time;
	}
	public void setReference_time(int reference_time) {
		this.reference_time = reference_time;
	}
	public int getWeek_number() {
		return week_number;
	}
	public void setWeek_number(int week_number) {
		this.week_number = week_number;
	}
	public int getLeap_seconds() {
		return leap_seconds;
	}
	public void setLeap_seconds(int leap_seconds) {
		this.leap_seconds = leap_seconds;
	}



	@Override
	public String toString() {
		return "NavigationHeader [version=" + version + ", ion_alpha=" + Arrays.toString(ion_alpha) + ", ion_beta="
				+ Arrays.toString(ion_beta) + ", a0_polynomial_term=" + a0_polynomial_term + ", a1_polynomial_term="
				+ a1_polynomial_term + ", reference_time=" + reference_time + ", week_number=" + week_number
				+ ", leap_seconds=" + leap_seconds + "]";
	}

}
