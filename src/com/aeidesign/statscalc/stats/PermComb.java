package com.aeidesign.statscalc.stats;

import java.math.BigInteger;

public class PermComb {
	
	public static long calcPigeonhole(long set, long group) {
		return set % group == 0? set/group : (set/group) + 1;
	}

	public static String calcNumSubset(long set) {
		return new BigInteger(String.valueOf(2)).pow((int)set).toString();
	}

	public static String calcCombinations(long set, long group) {
		return Functions.calcFactorial(set).divide((Functions.calcFactorial(group).multiply(Functions.calcFactorial(set - group)))).toString(); 
	}

	public static String calcCombinationsWithRep(long set, long group) {
		return Functions.calcFactorial(set + group - 1).divide((Functions.calcFactorial(group).multiply(Functions.calcFactorial(set - 1)))).toString();
	}

	public static String calcPermutationsWithRep(long set, long group) {
		return new BigInteger(String.valueOf(set)).pow((int)group).toString();
	}

	public static String calcPermutations(long set, long group) {
		return Functions.calcFactorial(set).divide(Functions.calcFactorial(set - group)).toString();
	}

}
