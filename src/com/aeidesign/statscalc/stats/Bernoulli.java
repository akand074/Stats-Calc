package com.aeidesign.statscalc.stats;

import java.math.BigDecimal;

public class Bernoulli {
	private double pLessThan;
	private double pLessThanOrEqual;
	private double pGreaterThan;
	private double pGreaterThanOrEqual;
	
	public Bernoulli() {
		pLessThan = 0;
		pLessThanOrEqual = 0;
		pGreaterThan = 0;
		pGreaterThanOrEqual = 0;
	}
	
	public double calcProbability(int x, int n, double p) {
		BigDecimal numerator = new BigDecimal(Functions.calcFactorial(n));
		BigDecimal denominator = new BigDecimal((Functions.calcFactorial(x)).multiply(Functions.calcFactorial(n - x)));
		BigDecimal retVal = numerator.divide(denominator, BigDecimal.ROUND_HALF_UP);
		retVal = retVal.multiply(new BigDecimal(Math.pow(p, x) * Math.pow(1-p, n-x)));
		return retVal.doubleValue();
	}
	
	public double calcProbabilityLessThan(int x, int n, double p){
		double probability = 0;
		
		if(pLessThanOrEqual != 0) {
			probability = pLessThanOrEqual;
			probability -=  calcProbability(x, n, p);
		} else {
			for(int i = 0; i < x; i++){
				probability += calcProbability(i, n, p);
			}
		}
		
		pLessThan = probability;
		return pLessThan;
	}
	
	public double calcProbabilityLessThanOrEqual(int x, int n, double p){
		double probability = 0;
		
		if(pLessThan != 0) {
			probability = pLessThan;
			probability += calcProbability(x, n, p);
		} else {
			for(int i = 0; i <= x; i++){
				probability += calcProbability(i, n, p);
			}
		}
		
		pLessThanOrEqual = probability;
		return pLessThanOrEqual;
	}
	
	public double calcProbabilityGreaterThan(int x, int n, double p){
		double probability = 0;
		
		if(pGreaterThanOrEqual != 0) {
			probability = pGreaterThanOrEqual;
			probability -=  calcProbability(x, n, p);
		} else {
			for(int i = x + 1; i <= n; i++){
				probability += calcProbability(i, n, p);
			}
		}
		
		pGreaterThan = probability;
		return pGreaterThan;
	}
	
	public double calcProbabilityGreaterThanOrEqual(int x, int n, double p){
		double probability = 0;
		
		if(pGreaterThan != 0) {
			probability = pGreaterThan;
			probability += calcProbability(x, n, p);
		} else {
			for(int i = x; i <= n; i++){
				probability += calcProbability(i, n, p);
			}
		}

		pGreaterThanOrEqual = probability;
		return pGreaterThanOrEqual;
	}

}
