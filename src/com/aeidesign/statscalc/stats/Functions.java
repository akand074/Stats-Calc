package com.aeidesign.statscalc.stats;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Functions {
	
	public static BigInteger calcFactorial(long value){
		
		BigInteger[] factorials = new BigInteger[(int) value + 1];
		factorials[0] = BigInteger.ONE;

		for (int i = 1; i <= value; i++) {
            factorials[i] = factorials[i - 1].multiply(new BigInteger("" + i));
        }
		
		return factorials[(int)value];
	}
	
	public static double format(double value){
		if ( Double.isNaN( value ) )
			return Double.NaN;
		
        BigDecimal val = BigDecimal.valueOf(value);
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
