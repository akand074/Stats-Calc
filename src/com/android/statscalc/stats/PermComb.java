package com.android.statscalc.stats;

public class PermComb {
	
	public static long calcPigeonhole(long set, long group) {
		return set % group == 0? set/group : (set/group) + 1;
	}

	public static long calcNumSubset(long set) {
		return (long) Math.pow(2, set);
	}

	public static long calcCombinations(long set, long group) {
		return Factorial.calcFactorial(set) / (Factorial.calcFactorial(group) * Factorial.calcFactorial(set - group)); 
	}

	public static long calcCombinationsWithRep(long set, long group) {
		return Factorial.calcFactorial(set + group - 1) / (Factorial.calcFactorial(group)*Factorial.calcFactorial(set - 1));
	}

	public static long calcPermutationsWithRep(long set, long group) {
		return (long) Math.pow(set, group);
	}

	public static long calcPermutations(long set, long group) {
		return Factorial.calcFactorial(set) / Factorial.calcFactorial(set - group);
	}

}
