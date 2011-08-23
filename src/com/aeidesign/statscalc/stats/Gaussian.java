package com.aeidesign.statscalc.stats;

public class Gaussian {
	
	public static double calcProbability(double x, double mean, double sd){
		return (x - mean) / sd;
	}
	
	public static double calcX(double probability, double mean, double sd){
		return probability * sd + mean;
	}

}
