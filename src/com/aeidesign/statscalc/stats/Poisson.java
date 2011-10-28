package com.aeidesign.statscalc.stats;

import java.math.BigDecimal;

public class Poisson {
	private double pLessThan;
	private double pLessThanOrEqual;
	private double pGreaterThan;
	private double pGreaterThanOrEqual;
	
	public Poisson(){
		pLessThan = 0;
		pLessThanOrEqual = 0;
		pGreaterThan = 0;
		pGreaterThanOrEqual = 0;
	}
	
	public double calcProbability(int x, double lambda) {
		
		BigDecimal bigLambda = new BigDecimal(lambda);
		BigDecimal retVal = new BigDecimal((bigLambda.pow(x)).toString());
		retVal = retVal.multiply(new BigDecimal(Math.pow(Math.E, lambda * -1)));
		retVal = retVal.divide( new BigDecimal( Functions.calcFactorial(x) ), BigDecimal.ROUND_HALF_UP );
		return retVal.doubleValue();
	}
	
	public double calcProbabilityLessThan(int x, double lambda){
		if(pLessThan != 0){
			return pLessThan;
		}
		
		if(x > 500 && x >= lambda * 2){
			return 1;
		}
		
		if(x > 500 && x <= lambda / 2){
			return 0;
		}
		
		double probability = 0;
		for(int i = 0; i < x; i ++){
			probability += calcProbability(i, lambda);
		}
		pLessThan = probability;
		return pLessThan;
	}
	
	public double calcProbabilityLessThanOrEqual(int x, double lambda){
		if(pLessThanOrEqual != 0){
			return pLessThanOrEqual;
		}
		
		if(x > 500 && x >= lambda * 2){
			return 1;
		}
		
		if(x > 500 && x <= lambda / 2){
			return 0;
		}
		
		double probability = 0;
		for(int i = 0; i <= x; i++){
			probability += calcProbability(i, lambda);
		}
		pLessThanOrEqual = probability;
		return pLessThanOrEqual;
	}
	
	public double calcProbabilityGreaterThan(int x, double lambda){
		if(x > 500 && x <= lambda / 2){
			return 1;
		}
		
		if(x > 500 && x >= lambda * 2){
			return 0;
		}
		
		if(pLessThanOrEqual == 0){
			pLessThanOrEqual = calcProbabilityLessThanOrEqual(x,lambda);
		}
		
		pGreaterThan = 1 - pLessThanOrEqual;
		
		if(pGreaterThan < 0){
			return 0;
		}
		
		return pGreaterThan;
	}
	
	public double calcProbabilityGreaterThanOrEqual(int x, double lambda){
		if(x > 500 && x <= lambda / 2){
			return 1;
		}
		
		if(x > 500 && x >= lambda * 2){
			return 0;
		}
		
		if(pLessThan == 0){
			pLessThan = calcProbabilityLessThan(x,lambda);
		}
		
		pGreaterThanOrEqual = 1 - pLessThan;
		
		if(pGreaterThanOrEqual < 0){
			return 0;
		}
		return pGreaterThanOrEqual;
	}

}
