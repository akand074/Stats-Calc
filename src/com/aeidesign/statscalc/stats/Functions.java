package com.aeidesign.statscalc.stats;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Functions {
	
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
	
	public static double format(double value){
        BigDecimal val = new BigDecimal(String.valueOf(value));
        MathContext mc = new MathContext(5);
        val = val.round(mc);
        return val.doubleValue();
    }
	
    public static String format(String value){
        BigDecimal val = new BigDecimal(value);
        MathContext mc = new MathContext(5);
        val = val.round(mc);
        return val.toString();
    }

}
