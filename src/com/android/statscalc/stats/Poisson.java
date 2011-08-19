package com.android.statscalc.stats;

import java.math.BigDecimal;

public class Poisson {
	
	public static double calcProbability(int x, double lambda) {
		return new BigDecimal(String.valueOf((Math.pow(lambda, x) * Math.pow(
				Math.E, lambda * -1)))).divide(
				new BigDecimal((Factorial.calcFactorial(x)))).doubleValue();
	}
	
	public static double calcProbabilityLessThan(int x, double lambda){
		double probability = 0;
		for(int i = 0; i < x; i ++){
			probability += calcProbability(i, lambda);
		}
		return probability;
	}
	
	public static double calcProbabilityLessThanOrEqual(int x, double lambda){
		double probability = 0;
		for(int i = 0; i <= x; i ++){
			probability += calcProbability(i, lambda);
		}
		return probability;
	}
	
	public static double calcProbabilityGreaterThan(int x, double lambda){
		return 1 - calcProbabilityLessThanOrEqual(x, lambda);
	}
	
	public static double calcProbabilityGreaterThanOrEqual(int x, double lambda){
		return 1 - calcProbabilityLessThan(x, lambda);
	}

}
