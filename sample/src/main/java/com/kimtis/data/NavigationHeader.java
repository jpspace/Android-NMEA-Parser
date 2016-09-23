package com.kimtis.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class NavigationHeader implements Serializable {

	@SerializedName("version")
	public String version;				// Rinex Format Version

	@SerializedName("ion_alpha")
	public double[] ion_alpha;			// Ion Alpha (A0)

	@SerializedName("ion_beta")
	public double[] ion_beta;			// Ion Beta (B0)

	@SerializedName("a0_polynomial_term")
	public double a0_polynomial_term;	// Almanac parameter to compute time in UTC : A0

	@SerializedName("a1_polynomial_term")
	public double a1_polynomial_term;	// Almanac parameter to compute time in UTC : A1

	@SerializedName("reference_time")
	public int reference_time;			// Reference time for UTC data

	@SerializedName("week_number")
	public int week_number;			// UTC reference week number

	@SerializedName("leap_seconds")
	public int leap_seconds;			// Number of leap seconds
	

	@Override
	public String toString() {
		return "NavigationHeader [version=" + version + ", ion_alpha=" + Arrays.toString(ion_alpha) + ", ion_beta="
				+ Arrays.toString(ion_beta) + ", a0_polynomial_term=" + a0_polynomial_term + ", a1_polynomial_term="
				+ a1_polynomial_term + ", reference_time=" + reference_time + ", week_number=" + week_number
				+ ", leap_seconds=" + leap_seconds + "]";
	}
	
}
