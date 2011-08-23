package com.aeidesign.statscalc.stats;

public class PermComb {
	
	public static long calcPigeonhole(long set, long group) {
		return set % group == 0? set/group : (set/group) + 1;
	}

	public static long calcNumSubset(long set) {
		return (long) Math.pow(2, set);
	}

	public static long calcCombinations(long set, long group) {
		return Functions.calcFactorial(set).divide((Functions.calcFactorial(group).multiply(Functions.calcFactorial(set - group)))).longValue(); 
	}

	public static long calcCombinationsWithRep(long set, long group) {
		return Functions.calcFactorial(set + group - 1).divide((Functions.calcFactorial(group).multiply(Functions.calcFactorial(set - 1)))).longValue();
	}

	public static long calcPermutationsWithRep(long set, long group) {
		return (long) Math.pow(set, group);
	}

	public static long calcPermutations(long set, long group) {
		return Functions.calcFactorial(set).divide(Functions.calcFactorial(set - group)).longValue();
	}

}
