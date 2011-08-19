package com.android.statscalc.stats;

import java.math.BigInteger;

public class Factorial {
	
	/*public static long calcFactorial(long value) {
		if(value <= 1){
			return 1;
		}
		return value * calcFactorial(value - 1);		
	}*/
	
	public static BigInteger calcFactorial(long value){
		BigInteger tempVal = new BigInteger(String.valueOf(value));
		while(value > 1){
			BigInteger current = new BigInteger(String.valueOf(value - 1));
			tempVal = tempVal.multiply(current);
			value--;
		}
		return tempVal;
	}

}
