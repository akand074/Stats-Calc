package com.aeidesign.statscalc.stats;

import java.math.BigInteger;

public class PermComb {
	
	public static int calcPigeonhole(int set, int group) {
		return set % group == 0? set/group : (set/group) + 1;
	}

	public static String calcNumSubset(int set) {
		return new BigInteger(String.valueOf(2)).pow(set).toString();
	}

	public static String calcCombinations(int set, int group) {
		return Functions.calcFactorial(set).divide((Functions.calcFactorial(group).multiply(Functions.calcFactorial(set - group)))).toString(); 
	}

	public static String calcCombinationsWithRep(int set, int group) {
		return Functions.calcFactorial(set + group - 1).divide((Functions.calcFactorial(group).multiply(Functions.calcFactorial(set - 1)))).toString();
	}

	public static String calcPermutationsWithRep(int set, int group) {
		return new BigInteger(String.valueOf(set)).pow((int)group).toString();
	}

	public static String calcPermutations(int set, int group) {
		return Functions.calcFactorial(set).divide(Functions.calcFactorial(set - group)).toString();
	}

}
