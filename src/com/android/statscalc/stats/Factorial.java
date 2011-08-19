package com.android.statscalc.stats;

import java.math.BigInteger;

public class Factorial {
	
	public static BigInteger calcFactorial(long value){
		if ( value == 0 )
			return BigInteger.valueOf(1);
		
		BigInteger tempVal = BigInteger.valueOf(value);
		
		while(value > 1){
			tempVal = tempVal.multiply( BigInteger.valueOf(value - 1) );
			value--;
		}
		
		return tempVal;
	}

}
