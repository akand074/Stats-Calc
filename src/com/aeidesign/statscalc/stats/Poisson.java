package com.aeidesign.statscalc.stats;

import java.math.BigDecimal;

public class Poisson {
	
	public static double calcProbability(int x, double lambda) {

			BigDecimal retVal = new BigDecimal( lambda );
			retVal = retVal.pow( x );
			
			retVal = retVal.multiply( new BigDecimal( Math.pow(Math.E, lambda * -1) ));
			retVal = retVal.divide( new BigDecimal( Functions.calcFactorial(x) ), BigDecimal.ROUND_HALF_UP );
			double dretVal = retVal.doubleValue();
			
			return dretVal;

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
		for(int i = 0; i <= x; i++){
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
