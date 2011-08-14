package com.android.statscalc.stats;

public class T {
	
	public static double calcT(double x, double mean, double se){
		return (x - mean) / se;
	}
	
	public static double calcX(double t, double mean, double se){
		return t * se + mean;
	}

}
