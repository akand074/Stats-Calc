package com.android.statscalc.stats;

public class Factorial {
	
	public static long calcFactorial(long value) {
		if(value <= 1){
			return 1;
		}
		return value * calcFactorial(value - 1);		
	}

}
