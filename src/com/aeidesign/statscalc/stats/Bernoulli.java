package com.aeidesign.statscalc.stats;

import java.math.BigDecimal;

public class Bernoulli {
	
	public static double calcProbability(int x, int n, double p) {
		BigDecimal numerator = new BigDecimal(Functions.calcFactorial(n));
		BigDecimal denominator = new BigDecimal((Functions.calcFactorial(x)).multiply(Functions.calcFactorial(n - x)));
		BigDecimal retVal = numerator.divide(denominator, BigDecimal.ROUND_HALF_UP);
		retVal = retVal.multiply(new BigDecimal(Math.pow(p, x) * Math.pow(1-p, n-x)));
		return retVal.doubleValue();
	}
	
	public static double calcProbabilityLessThan(int x, int n, double p){
		double probability = 0;
		for(int i = 0; i < x; i++){
			probability += calcProbability(i, n, p);
		}
		return probability;
	}
	
	public static double calcProbabilityLessThanOrEqual(int x, int n, double p){
		double probability = 0;
		for(int i = 0; i <= x; i++){
			probability += calcProbability(i, n, p);
		}
		return probability;
	}
	
	public static double calcProbabilityGreaterThan(int x, int n, double p){
		double probability = 0;
		for(int i = x + 1; i <= n; i++){
			probability += calcProbability(i, n, p);
		}
		return probability;
	}
	
	public static double calcProbabilityGreaterThanOrEqual(int x, int n, double p){
		double probability = 0;
		for(int i = x; i <= n; i++){
			probability += calcProbability(i, n, p);
		}
		return probability;
	}

}
